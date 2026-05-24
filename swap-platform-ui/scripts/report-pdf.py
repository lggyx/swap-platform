#!/usr/bin/env python3
"""
Swap Platform PDF Report Generator
Usage: python scripts/report-pdf.py [reportPath] [outputPath]
  reportPath  default: scripts/last-report.json
  outputPath  default: scripts/last-report.pdf

Tries weasyprint first, falls back to reportlab, then HTML.
"""

import json
import sys
import os
from pathlib import Path


def load_report(report_path):
    with open(report_path, 'r', encoding='utf-8') as f:
        return json.load(f)


def generate_html(report):
    """Generate styled HTML from report data."""
    summary = report.get('summary', {})
    pass_rate = (summary.get('pass', 0) / summary.get('total', 1) * 100) if summary.get('total', 0) > 0 else 0
    icon = '🟢' if summary.get('fail', 0) == 0 else ('🟡' if summary.get('fail', 0) <= summary.get('total', 1) / 2 else '🔴')

    html = f"""<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<title>Swap Platform API 测试报告</title>
<style>
  body {{ font-family: "Microsoft YaHei", "SimHei", Arial, sans-serif; margin: 40px; color: #333; }}
  h1 {{ text-align: center; color: #1C2833; }}
  .summary {{ background: #f5f7fa; padding: 20px; border-radius: 8px; margin: 20px 0; }}
  table {{ border-collapse: collapse; width: 100%; margin: 16px 0; }}
  th, td {{ border: 1px solid #ddd; padding: 10px 14px; text-align: left; }}
  th {{ background: #1C2833; color: #fff; }}
  tr:nth-child(even) {{ background: #f9f9f9; }}
  .pass {{ color: #28a745; }}
  .fail {{ color: #dc3545; }}
  .warn {{ color: #ffc107; }}
  .section {{ margin: 24px 0; }}
  .case {{ background: #fff; border: 1px solid #eee; padding: 12px; margin: 8px 0; border-radius: 4px; }}
  .case-title {{ font-weight: bold; }}
  .meta {{ color: #666; font-size: 0.9em; }}
  .raw {{ background: #f4f6f6; padding: 8px; border-radius: 4px; font-family: monospace; font-size: 0.85em; word-break: break-all; }}
</style>
</head>
<body>
<h1>Swap Platform API 测试报告</h1>

<div class="summary">
  <table>
    <tr><th>属性</th><th>值</th></tr>
    <tr><td>时间</td><td>{report.get('time', 'N/A')}</td></tr>
    <tr><td>目标</td><td>{report.get('baseUrl', 'N/A')}</td></tr>
    <tr><td>状态</td><td>{icon} {summary.get('pass', 0)}/{summary.get('total', 0)} 通过 ({pass_rate:.1f}%)</td></tr>
    <tr><td>通过</td><td class="pass">{summary.get('pass', 0)}</td></tr>
    <tr><td>失败</td><td class="fail">{summary.get('fail', 0)}</td></tr>
  </table>
</div>

<div class="section">
  <h2>分类统计</h2>
  <table>
    <tr><th>模块</th><th>结果</th></tr>
"""
    groups = report.get('groups', {})
    for group, result in groups.items():
        parts = result.split('/')
        p, t = int(parts[0]), int(parts[1])
        cls = 'pass' if p == t else ('fail' if p == 0 else 'warn')
        emoji = '✅' if p == t else ('❌' if p == 0 else '⚠️')
        html += f'    <tr><td>{emoji} {group}</td><td class="{cls}">{p}/{t} 通过</td></tr>\n'

    html += '  </table>\n</div>\n'

    # Failures
    failed = report.get('failed', [])
    if failed:
        html += '<div class="section"><h2>失败用例</h2>\n'
        for f in failed:
            html += f"""<div class="case">
  <div class="case-title">{f.get('name', 'Unknown')}</div>
  <div class="meta">HTTP: {f.get('status', 'N/A')} | code: {f.get('code', 'N/A')} | msg: {f.get('msg', 'N/A')}</div>
  <div class="raw">{f.get('raw', 'N/A')}</div>
</div>
"""
        html += '</div>\n'

    # Differences
    diff = report.get('diff', [])
    if diff:
        html += '<div class="section"><h2>契约差异</h2><ul>\n'
        for d in diff:
            html += f'  <li>{d}</li>\n'
        html += '</ul></div>\n'

    # Uncovered
    uncovered = report.get('uncovered', [])
    if uncovered:
        html += '<div class="section"><h2>未覆盖接口</h2><ul>\n'
        for u in uncovered:
            html += f'  <li><code>{u.get("method", "")} {u.get("path", "")}</code> - {u.get("summary", "")}</li>\n'
        html += '</ul></div>\n'

    # Passed
    passed = report.get('passed', [])
    html += f'<div class="section"><h2>通过用例 ({len(passed)})</h2><ul>\n'
    for name in passed:
        html += f'  <li class="pass">{name}</li>\n'
    html += '</ul></div>\n'

    html += '<hr><p style="color:#999;text-align:center;">报告由 swap-test-report skill 自动生成</p>\n</body>\n</html>'
    return html


def try_weasyprint(html, output_path):
    from weasyprint import HTML
    HTML(string=html).write_pdf(output_path)
    return True


def try_reportlab(report, output_path):
    from reportlab.lib import colors
    from reportlab.lib.pagesizes import A4
    from reportlab.platypus import SimpleDocTemplate, Table, TableStyle, Paragraph, Spacer
    from reportlab.lib.styles import getSampleStyleSheet
    from reportlab.pdfbase import pdfmetrics
    from reportlab.pdfbase.ttfonts import TTFont
    from reportlab.lib.units import cm

    # Register Chinese fonts
    font_paths = [
        '/usr/share/fonts/truetype/wqy/wqy-zenhei.ttc',
        '/usr/share/fonts/truetype/droid/DroidSansFallbackFull.ttf',
        '/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf',
    ]
    font_name = 'Helvetica'
    for fp in font_paths:
        if os.path.exists(fp):
            try:
                pdfmetrics.registerFont(TTFont('ChineseFont', fp))
                font_name = 'ChineseFont'
                break
            except Exception:
                continue

    doc = SimpleDocTemplate(str(output_path), pagesize=A4,
                            rightMargin=2*cm, leftMargin=2*cm,
                            topMargin=2*cm, bottomMargin=2*cm)
    styles = getSampleStyleSheet()
    story = []

    # Title
    title_style = styles['Title']
    story.append(Paragraph('Swap Platform API 测试报告', title_style))
    story.append(Spacer(1, 0.5*cm))

    # Summary table
    summary = report.get('summary', {})
    pass_rate = (summary.get('pass', 0) / summary.get('total', 1) * 100) if summary.get('total', 0) > 0 else 0
    data = [['属性', '值'],
            ['时间', report.get('time', 'N/A')],
            ['目标', report.get('baseUrl', 'N/A')],
            ['状态', f"{summary.get('pass', 0)}/{summary.get('total', 0)} 通过 ({pass_rate:.1f}%)"],
            ['通过', str(summary.get('pass', 0))],
            ['失败', str(summary.get('fail', 0))]]
    t = Table(data, colWidths=[4*cm, 12*cm])
    t.setStyle(TableStyle([
        ('BACKGROUND', (0, 0), (-1, 0), colors.HexColor('#1C2833')),
        ('TEXTCOLOR', (0, 0), (-1, 0), colors.whitesmoke),
        ('FONTNAME', (0, 0), (-1, -1), font_name),
        ('FONTSIZE', (0, 0), (-1, 0), 12),
        ('ALIGN', (0, 0), (-1, -1), 'LEFT'),
        ('GRID', (0, 0), (-1, -1), 0.5, colors.grey),
        ('VALIGN', (0, 0), (-1, -1), 'TOP'),
    ]))
    story.append(t)
    story.append(Spacer(1, 0.5*cm))

    # Groups
    groups = report.get('groups', {})
    if groups:
        story.append(Paragraph('分类统计', styles['Heading2']))
        gdata = [['模块', '结果']]
        for group, result in groups.items():
            parts = result.split('/')
            p, t_val = int(parts[0]), int(parts[1])
            gdata.append([group, f"{p}/{t_val} 通过"])
        gt = Table(gdata, colWidths=[6*cm, 10*cm])
        gt.setStyle(TableStyle([
            ('BACKGROUND', (0, 0), (-1, 0), colors.HexColor('#2E4053')),
            ('TEXTCOLOR', (0, 0), (-1, 0), colors.whitesmoke),
            ('FONTNAME', (0, 0), (-1, -1), font_name),
            ('GRID', (0, 0), (-1, -1), 0.5, colors.grey),
        ]))
        story.append(gt)
        story.append(Spacer(1, 0.5*cm))

    # Failures
    failed = report.get('failed', [])
    if failed:
        story.append(Paragraph(f'失败用例 ({len(failed)})', styles['Heading2']))
        for f in failed:
            story.append(Paragraph(f"<b>{f.get('name', 'Unknown')}</b>", styles['Heading3']))
            story.append(Paragraph(f"HTTP: {f.get('status', 'N/A')} | code: {f.get('code', 'N/A')} | msg: {f.get('msg', 'N/A')}", styles['Normal']))
            story.append(Paragraph(f"原始: {f.get('raw', 'N/A')[:200]}", styles['Code']))
            story.append(Spacer(1, 0.3*cm))

    # Passed
    passed = report.get('passed', [])
    if passed:
        story.append(Paragraph(f'通过用例 ({len(passed)})', styles['Heading2']))
        for name in passed:
            story.append(Paragraph(name, styles['Normal']))

    doc.build(story)
    return True


def generate_pdf(report, output_path):
    html = generate_html(report)

    # Try weasyprint
    try:
        try_weasyprint(html, output_path)
        print(f"PDF report (weasyprint): {output_path}")
        return
    except Exception as e:
        print(f"weasyprint failed: {e}")

    # Try reportlab
    try:
        try_reportlab(html, output_path)
        print(f"PDF report (reportlab): {output_path}")
        return
    except Exception as e:
        print(f"reportlab failed: {e}")

    # Fallback: HTML
    html_path = str(output_path).replace('.pdf', '.html')
    with open(html_path, 'w', encoding='utf-8') as f:
        f.write(html)
    print(f"PDF generation failed. HTML fallback: {html_path}")


if __name__ == '__main__':
    report_path = sys.argv[1] if len(sys.argv) > 1 else 'last-report.json'
    output_path = sys.argv[2] if len(sys.argv) > 2 else 'last-report.pdf'

    script_dir = Path(__file__).parent
    if not os.path.isabs(report_path):
        report_path = script_dir / report_path
    if not os.path.isabs(output_path):
        output_path = script_dir / output_path

    report = load_report(str(report_path))
    generate_pdf(report, str(output_path))

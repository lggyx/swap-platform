// Swap Platform Markdown Report Generator
// Usage: node scripts/report-generator.js [reportPath] [outputPath]
//   reportPath  default: scripts/last-report.json
//   outputPath  default: scripts/last-report.md

import { readFileSync, writeFileSync, mkdirSync } from 'fs'

const reportPath = process.argv[2] || 'scripts/last-report.json'
const outputPath = process.argv[3] || 'scripts/last-report.md'

try { mkdirSync('scripts', { recursive: true }) } catch {}

let report
try {
  report = JSON.parse(readFileSync(reportPath, 'utf-8'))
} catch (e) {
  console.error(`Cannot read ${reportPath}: ${e.message}`)
  console.error('Run: node scripts/api-test-auto.js --once')
  process.exit(1)
}

function generateMarkdown(report) {
  const { time, baseUrl, summary, groups, failed, passed, diff, uncovered } = report
  const passRate = summary.total > 0 ? ((summary.pass / summary.total) * 100).toFixed(1) : '0.0'
  let icon = '🟢'
  if (summary.fail > 0 && summary.fail <= summary.total / 2) icon = '🟡'
  else if (summary.fail > summary.total / 2) icon = '🔴'

  let md = ''
  md += `# Swap Platform API 测试报告\n\n`
  md += `| 属性 | 值 |\n|------|----|\n`
  md += `| 时间 | ${time} |\n`
  md += `| 目标 | ${baseUrl} |\n`
  md += `| 状态 | ${icon} ${summary.pass}/${summary.total} 通过 (${passRate}%) |\n\n`

  md += `## 分类统计\n\n`
  md += `| 模块 | 结果 |\n|------|------|\n`
  for (const [group, result] of Object.entries(groups)) {
    const [p, t] = result.split('/').map(Number)
    const e = p === t ? '✅' : p === 0 ? '❌' : '⚠️'
    md += `| ${e} ${group} | ${p}/${t} 通过 |\n`
  }
  md += `\n`

  if (failed && failed.length > 0) {
    md += `## ❌ 失败用例 (${failed.length})\n\n`
    for (const f of failed) {
      md += `### ${f.name}\n\n`
      md += `- HTTP 状态: ${f.status || 'N/A'}\n`
      md += `- 响应 code: ${f.code}\n`
      md += `- 响应 msg: ${f.msg}\n`
      md += `- 原始返回: \`${f.raw}\`\n\n`
    }
  }

  if (diff && diff.length > 0) {
    md += `## ⚠️ 契约差异\n\n`
    for (const d of diff) {
      md += `- ${d}\n`
    }
    md += `\n`
  }

  if (uncovered && uncovered.length > 0) {
    md += `## ⚠️ 未覆盖接口\n\n`
    for (const u of uncovered) {
      md += `- \`${u.method} ${u.path}\` - ${u.summary || '无描述'}\n`
    }
    md += `\n`
  }

  md += `## ✅ 通过用例 (${passed.length})\n\n`
  for (const name of passed) {
    md += `- ${name}\n`
  }
  md += `\n---\n*报告由 swap-test-report skill 自动生成*\n`

  return md
}

const markdown = generateMarkdown(report)
writeFileSync(outputPath, markdown, 'utf-8')

const failRate = report.summary.total > 0 ? (report.summary.fail / report.summary.total * 100).toFixed(1) : 0
const statusIcon = report.summary.fail === 0 ? '✅' : (report.summary.fail <= report.summary.total / 2 ? '⚠️' : '❌')
console.log(`\n══════════════════════════════════════════════`)
console.log(`${statusIcon} SKILL: swap-test-report 完成`)
console.log(`   通过: ${report.summary.pass}/${report.summary.total} (${(100 - failRate)}%) | 失败: ${report.summary.fail}`)
console.log(`   输出: ${outputPath}`)
console.log(`══════════════════════════════════════════════`)

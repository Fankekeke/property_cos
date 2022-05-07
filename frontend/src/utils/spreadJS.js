// import { message } from 'ant-design-vue/es'
import message from 'ant-design-vue/es/message'

/* eslint-disable no-undef */
const newSpread = tableName => {
  let spread = new GC.Spread.Sheets.Workbook(document.getElementById('ss'))
  // 从本地读取JSON模板 导入到spread里
  spread.fromJSON(require(`../../static/file/${fileNameData[tableName]}`))
  return spread
}

// 固定形式
const fixedForm = (spread, tableName, dataObject) => {
  let sheet = spread.getActiveSheet()
  sheet.suspendPaint()
  let data = rowColData[tableName]
  Object.keys(dataObject).forEach(key => { // key就是列名
    if (data[key]) {
      sheet.setValue(data[key].row, data[key].col, dataObject[key])
    }
  })
  sheet.resumePaint()
  return spread
}

// 浮动形式
const floatForm = (spread, tableName, dataSource) => {
  let sheet = spread.getActiveSheet()
  sheet.suspendPaint()
  let startRow = startRowData[tableName] // startRowData里记录的是编号 index为 编号减1
  sheet.addRows(startRow, dataSource.length - 1) // length - 1 是因为原本就有一行
  for (let i = 0, len = dataSource.length - 1; i < len; i++) {
    sheet.copyTo(startRow - 1, 1, startRow + i, 1, 1, sheet.getColumnCount() - 1, GC.Spread.Sheets.CopyToOptions.all)
    sheet.setRowHeight(startRow + i, sheet.getRowHeight(startRow - 1))
  }
  // 这里开始插入数据
  sheet.setArray(startRow - 1, 1, dataSource)
  sheet.resumePaint()
  return spread
}

const floatReset = (spread, tableName, dataSourceLength) => {
  let sheet = spread.getActiveSheet()
  let startRow = startRowData[tableName]
  sheet.deleteRows(startRow, dataSourceLength - 1)
}

// 导出操作
const saveExcel = (spread, fileName) => {
  let excelIo = new GC.Spread.Excel.IO()
  excelIo.save(spread.toJSON(), blob => {
    const link = document.createElement('a')
    link.download = fileName
    link.style.display = 'none'
    link.href = URL.createObjectURL(blob)
    link.click()
    URL.revokeObjectURL(link.href)
  }, e => {
    console.log(e)
    message.error('Failed to export file')
  })
}

// 表名对应的JSON模板文件名
const fileNameData = {
  textTable: 'text.json'
}

// 表名对应的开始行index
const startRowData = {
  textTable: 1
}

// 表名对应的各列名，列名又对应行数和列数
const rowColData = {
  textTable: {
    title: {row: 0, col: 1}
  }
}

export { newSpread, fixedForm, floatForm, floatReset, saveExcel }

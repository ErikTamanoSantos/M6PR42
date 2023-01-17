const express = require('express')
const fs = require('fs/promises')
const url = require('url')
const post = require('./post.js')

// Iniciar servidors HTTP i WebSockets
const app = express()

// Configurar el port del servidor HTTP
const port = process.env.PORT || 3000

// Publicar els arxius HTTP de la carpeta 'public'
app.use(express.static('public'))

// Activar el servidor HTTP
const httpServer = app.listen(port, appListen)
function appListen () {
  console.log(`Example app listening for HTTP queries on: http://localhost:${port}`)
}

// Definir URL per les dades tipus POST
app.post('/dades', getDades)
async function getDades (req, res) {
  let receivedPOST = await post.getPostData(req)
  let result = {};

  if (receivedPOST) {
    if (receivedPOST.type == "consola") {
      var arxiuText = await fs.readFile("./public/consoles/llista-dades.json", { encoding: 'utf8'})
      var objLlistaConsoles = JSON.parse(arxiuText)
      result = { result: dadesConsola(objLlistaConsoles, receivedPOST.name) }
    }
  }

  res.writeHead(200, { 'Content-Type': 'application/json' })
  res.end(JSON.stringify(result))
}

// Funció que cerca les dades d'una consola en una llista
function dadesConsola (list, name) {
  for (let i = 0; i < list.length; i++) {
    if (list[i].name == name) {
      return list[i]
    }
  }
  return null
}
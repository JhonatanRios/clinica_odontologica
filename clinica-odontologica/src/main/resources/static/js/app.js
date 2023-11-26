// Const
const urlApi = 'http://localhost:8081';
const contListar = document.querySelector('#listarAll');


// Función para realizar peticiones Fetch genéricas
async function realizarPeticion(metodo, url, datos = null) {
  const opciones = {
    method: metodo,
    headers: {
      'Content-Type': 'application/json'
    },
    body: datos ? JSON.stringify(datos) : null
  };
  const response = await fetch(url, opciones);

  if (!response.ok) {
    throw new Error(`Error en la petición: ${response.statusText}`);
  }
  return response.json();
}



// Listar cards
function listarCards(data, div, tipoEntidad) {
  div.innerHTML = '';
  data.forEach(item => {
    const card = document.createElement('div');
    card.classList.add('d-grid', 'center-start', 'item', 'g--10', 'p--15');

    let plantillaCard;
    switch (tipoEntidad) {
      case 'odontologo':
        plantillaCard = crearCardOdontologo(item);
        break;
      case 'paciente':
        plantillaCard = crearCardPaciente(item);
        break;
      case 'turno':
        plantillaCard = crearCardTurno(item);
        break;
      default:
        console.error('Tipo de entidad no reconocido:', tipoEntidad);
        break;
    }

    card.innerHTML = plantillaCard;
    div.appendChild(card);
  });
  
  setTimeout(() => {
    const elementLoad = document.querySelectorAll('.load');
    elementLoad.forEach(e => {
      e.classList.remove('load');
    });
  }, 2000);
}



// Validaciones
// Función para validar si una cadena contiene solo letras
function esSoloLetras(cadena) {
  const regex = /^[a-zA-Z\s]+$/;
  return regex.test(cadena);
}
// Función para validar si una cadena es un número
function esNumero(cadena) {
  return !isNaN(cadena);
}

// Formatear fecha
function formatearFecha(fecha) {
  const fechaObj = new Date(fecha);
  
  const dia = fechaObj.getDate();
  const mes = fechaObj.getMonth() + 1;
  const anio = fechaObj.getFullYear();
  
  const diaFormateado = dia < 10 ? `0${dia}` : dia;
  const mesFormateado = mes < 10 ? `0${mes}` : mes;
  
  return `${diaFormateado}-${mesFormateado}-${anio}`;
}
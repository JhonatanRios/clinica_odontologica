// Variables
const urlApi = 'http://localhost:8081';
const urlActual = window.location.href;
const contListar = document.querySelector('#listarAll');
const inputBuscar = document.getElementById('buscar');

const entidad = entidadUrl(urlActual);

// Peticiones Fetch
async function realizarPeticion(metodo, url, datos = null) {
  const opciones = {
    method: metodo,
    headers: {
      'Content-Type': 'application/json'
    },
    body: datos ? JSON.stringify(datos) : null
  };
  const response = await fetch(url, opciones);

  if (!response.ok) throw new Error(`Error en la petición: ${response.statusText}`);

  // Verificar si la respuesta es de tipo JSON
  const contentType = response.headers.get('Content-Type');
  if (contentType && contentType.includes('application/json')) {
    return response.json();
  } else return response.text();
}




// Crear
function modalCrear(crearContenido, callback) {
  Swal.fire({
    title: 'Registro',
    html: crearContenido(),
    showCancelButton: true,
    confirmButtonText: 'Registrar',
    cancelButtonText: 'Cancelar',
    preConfirm: () => {
      let datosValidados;

      switch (entidad) {
        case 'odontologo':
          datosValidados = validarOdontologo();
          break;
        default:
          break;
      }

      return datosValidados;
    }
  }).then((result) => {
    if (result.isConfirmed && result.value) {
      callback(result.value);
    }
  });
}
// Listar
function listarCards(data, div) {
  div.innerHTML = '';
  data.forEach(item => {
    const card = document.createElement('div');
    card.classList.add('d-grid', 'center-start', 'item', 'g--10', 'p--15');
    card.id = `${item.id}`;

    let plantillaCard;
    switch (entidad) {
      case 'odontologo':
        plantillaCard = cardOdontologo(item);
        break;
      case 'paciente':
        plantillaCard = cardPaciente(item);
        break;
      case 'turno':
        plantillaCard = cardTurno(item);
        break;
      default:
        console.error('Tipo de entidad no reconocido:', entidad);
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
  }, 1000);
}
// Buscar por ID
inputBuscar.addEventListener('input', () => {
  const searchTerm = inputBuscar.value.trim();
  if (searchTerm !== '') {
    switch (entidad) {
      case "odontologo":
        buscarPorIdOdontologo(searchTerm);
        break;
      case "paciente":
        // Lógica para el caso de paciente
        console.log("Es una página de paciente");
        break;
      case "turno":
        // Lógica para el caso de turno
        console.log("Es una página de turno");
        break;
      default:
        console.error("No se reconoce la entidad en la URL");
        break;
    }
  }
});
// Eliminar
function confirmarEliminar(id) {
  Swal.fire({
    title: '¿Estás seguro?',
    text: 'Esta acción no se puede deshacer',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: 'Sí, eliminarlo'
  }).then((result) => {
    if (result.isConfirmed) {
      switch (entidad) {
        case "odontologo":
          eliminarOdontologo(id);
          break;
        case "paciente":
          // Lógica para el caso de paciente
          console.log("Es una página de paciente");
          break;
        case "turno":
          // Lógica para el caso de turno
          console.log("Es una página de turno");
          break;
        default:
          console.error("No se reconoce la entidad en la URL");
          break;
      }
    }
  });
}



// Validaciones
function validarOdontologo() {
  const nombreInput = Swal.getPopup().querySelector('#nombre');
  const apellidoInput = Swal.getPopup().querySelector('#apellido');
  const matriculaInput = Swal.getPopup().querySelector('#matricula');

  // Obtener los valores de los campos
  const nombre = nombreInput.value.trim();
  const apellido = apellidoInput.value.trim();
  const matricula = matriculaInput.value.trim();

  // Validar que los campos no estén vacíos
  if (!nombre || !apellido || !matricula) {
    Swal.showValidationMessage('Por favor, completa todos los campos.');
    return false;
  }

  const datosOdontologo = { nombre, apellido, matricula };
  return datosOdontologo;
}
// Validar si es solo letras
function esSoloLetras(cadena) {
  const regex = /^[a-zA-Z\s]+$/;
  return regex.test(cadena);
}
// Validar si es un número
function esNumero(cadena) {
  return !isNaN(cadena);
}
// Formatear fecha y hora
function formatearFecha(fecha) {
  const fechaObj = new Date(fecha);
  const dia = fechaObj.getDate();
  const mes = fechaObj.getMonth() + 1;
  const anio = fechaObj.getFullYear();
  const diaFormateado = dia < 10 ? `0${dia}` : dia;
  const mesFormateado = mes < 10 ? `0${mes}` : mes;
  return `${diaFormateado}-${mesFormateado}-${anio}`;
}
function formatearHora(fecha) {
  const fechaObj = new Date(fecha);
  const opcionesHora = { hour: '2-digit', minute: '2-digit' };
  const horaFormateada = fechaObj.toLocaleTimeString(undefined, opcionesHora);
  return horaFormateada;
}
// Obtener la entidad de la Url
function entidadUrl(url) {
  const entidadRegex = /(odontologo|paciente|turno)/;
  const match = url.match(entidadRegex);
  return match ? match[0] : null;
}
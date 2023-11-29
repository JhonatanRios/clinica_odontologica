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
function buscarPorId(id) {
  realizarPeticion('GET', `${urlApi}/${entidad}s/${id}`).then(data => {
    const dataArray = Array.isArray(data) ? data : [data];
    listarCards(dataArray, contListar);
    switch (entidad) {
      case 'odontologo':
        odontologoLocal(dataArray);
        break;
      case 'paciente':
        pacienteLocal(dataArray);
        break;
      default:
        break;
    }
  })
  .catch(error => {
    console.error('ID no encontrado:', error);
    contListar.innerHTML = `<p class="txt--center">ID #${id} no encontrado</p>`;
  });
}
inputBuscar.addEventListener('input', () => {
  const searchTerm = inputBuscar.value.trim();
  if (searchTerm !== '') {
    buscarPorId(searchTerm);
  }
});
// Actualizar
function modalActualizar(actualizarContenido, callback) {
  Swal.fire({
    title: 'Actualizar',
    html: actualizarContenido(),
    showCancelButton: true,
    confirmButtonText: 'Actualizar',
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
          eliminarPaciente(id);
          break;
        case "turno":
          eliminarTurno(id);
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
function ajustarFechaUTC(fecha) {
  const fechaObj = new Date(fecha);
  return new Date(fechaObj.getUTCFullYear(), fechaObj.getUTCMonth(), fechaObj.getUTCDate());
}
function formatearFecha(fecha) {
  const fechaAjustada = ajustarFechaUTC(fecha);
  const dia = fechaAjustada.getDate();
  const mes = fechaAjustada.getMonth() + 1;
  const anio = fechaAjustada.getFullYear();
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
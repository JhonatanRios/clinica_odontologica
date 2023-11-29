const urlOdontologo = `${urlApi}/odontologos`;
let odontologos = [];

document.addEventListener('DOMContentLoaded', async () => {
  try {
    const data = await realizarPeticion('GET', `${urlOdontologo}/listar`);
    if (data.length === 0) {
      contListar.innerHTML = '<p class="txt--center p--15">No hay datos disponibles.</p>';
    } else {
      listarCards(data, contListar);
      odontologoLocal(data);
    }
  } catch (error) {
    console.error('Error al obtener los odontólogos:', error);
    contListar.innerHTML = 'Error al cargar los odontólogos.';
  }
});
// Registrar
async function registrarOdontologo(datos) {
  try {
    await realizarPeticion('POST', `${urlOdontologo}/registrar`, datos);
    const data = await realizarPeticion('GET', `${urlOdontologo}/listar`);
    listarCards(data, contListar);
    odontologoLocal(data);
  } catch (error) {
    console.error('Error al registrar al odontólogo:', error);
  }
}
// Actualizar
function actualizarOdontologo(datos) {
  realizarPeticion('PUT', `${urlOdontologo}/actualizar`, datos)
    .then(() => {
      return realizarPeticion('GET', `${urlOdontologo}/listar`);
    })
    .then(data => {
      listarCards(data, contListar);
      odontologoLocal(data);
    })
    .catch(error => {
      console.error('Error al actualizar el odontólogo:', error);
    });
}
// Eliminar
function eliminarOdontologo(id) {
  realizarPeticion('DELETE', `${urlOdontologo}/eliminar/${id}`).then(() => {
    Swal.fire(
      'Eliminado',
      'El odontólogo ha sido eliminado correctamente.',
      'success'
    );
    return realizarPeticion('GET', `${urlOdontologo}/listar`);
  })
  .then(data => {
    listarCards(data, contListar);
    odontologoLocal(data);
  })
  .catch(error => {
    console.error('Error al eliminar al odontólogo: ', error);
  });
}


function odontologoLocal(data) {
  odontologos = data;
}

// Templates
// Form
function formCrearOdontologo() {
  modalCrear(() => `
    <form class="d-grid g--10" id="registroForm">
      <label for="nombre">Nombre:</label>
      <input type="text" id="nombre" name="nombre" required>
      <label for="apellido">Apellido:</label>
      <input type="text" id="apellido" name="apellido" required>
      <label for="matricula">Matrícula:</label>
      <input type="text" id="matricula" name="matricula" required>
    </form>
  `, (datos) => {
    if (datos) {
      registrarOdontologo(datos);
    }
  });
}
function formActualizarOdontologo(id) {
  const odontoLocal = odontologos.find(odontologo => odontologo.id === id);
  modalActualizar(() => `
    <form class="d-grid g--10" id="registroForm">
      <label for="nombre">Nombre:</label>
      <input type="text" id="nombre" name="nombre" value="${odontoLocal.nombre}" required>
      <label for="apellido">Apellido:</label>
      <input type="text" id="apellido" name="apellido" value="${odontoLocal.apellido}" required>
      <label for="matricula">Matrícula:</label>
      <input type="text" id="matricula" name="matricula" value="${odontoLocal.matricula}" required>
    </form>
  `, (datos) => {
    if (datos) {
      datos.id = id;
      actualizarOdontologo(datos);
    }
  });
}
// Card
function cardOdontologo(item) {
  return `
    <div class="info">
      <div class="image load">
        <img src="./assets/odontologo.png" alt="">
      </div>
      <div class="d-grid details g--5">
        <span class="id load"><strong>Id: ${item.id}</strong></span>
        <span class="load"><strong>Nombre:</strong> ${item.nombre} ${item.apellido}</span>
        <span class="load"><strong>Matricula:</strong> ${item.matricula}</span>
      </div>
    </div>
    <div class="d-flex cont-btns g--10">
      <button class="bg--blue btn load" onclick="formActualizarOdontologo(${item.id})"><i class="fa fa-refresh" aria-hidden="true"></i> Actualizar</button>
      <button class="bg--red btn load" onclick="confirmarEliminar(${item.id})"><i class="fa fa-trash" aria-hidden="true"></i></button>
    </div>
  `;
}
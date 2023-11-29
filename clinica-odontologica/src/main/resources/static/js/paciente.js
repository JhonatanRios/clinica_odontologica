const urlPaciente = `${urlApi}/pacientes`;
let pacientes = [];

document.addEventListener('DOMContentLoaded', async () => {
  try {
    const data = await realizarPeticion('GET', `${urlPaciente}/listar`);
    if (data.length === 0) {
      contListar.innerHTML = '<p class="txt--center p--15">No hay datos disponibles.</p>';
    } else {
      listarCards(data, contListar);
      pacienteLocal(data);
    }
  } catch (error) {
    console.error('Error al obtener los pacientes:', error);
    contListar.innerHTML = 'Error al cargar los pacientes.';
  }
});
// Eliminar
function eliminarPaciente(id) {
  realizarPeticion('DELETE', `${urlPaciente}/eliminar/${id}`).then(() => {
    Swal.fire(
      'Eliminado',
      'El paciente ha sido eliminado correctamente.',
      'success'
    );
    return realizarPeticion('GET', `${urlPaciente}/listar`);
  })
  .then(data => {
    listarCards(data, contListar);
    pacienteLocal(data);
  })
  .catch(error => {
    console.error('Error al eliminar al paciente: ', error);
  });
}


function pacienteLocal(data) {
  pacientes = data;
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
// Card
function cardPaciente(item) {
  return `
    <div class="info">
      <div class="image load">
        <img src="./assets/paciente.png" alt="">
      </div>
      <div class="d-grid details g--10">
        <span class="id load"><strong>Id: ${item.id}</strong></span>
        <span class="load"><strong>Nombre:</strong> ${item.nombre} ${item.apellido}</span>
        <span class="load"><strong>DNI:</strong> ${item.dni}</span>
        <span class="load"><strong>Fecha de ingreso:</strong> ${formatearFecha(item.fechaIngreso)}</span>
        <details class="load">
          <summary><strong><span>Domicilio:</span></strong></summary>
          <span><strong>Dirección:</strong> ${item.domicilio.calle} ${item.domicilio.numero} / ${item.domicilio.localidad} - ${item.domicilio.provincia}</span>
        </details>
      </div>
    </div>
    <div class="d-flex cont-btns g--10">
      <button class="bg--blue btn load" onclick="formActualizarPaciente(${item.id})"><i class="fa fa-refresh" aria-hidden="true"></i> Actualizar</button>
      <button class="bg--red btn load" onclick="confirmarEliminar(${item.id})"><i class="fa fa-trash" aria-hidden="true"></i></button>
    </div>
  `;
}
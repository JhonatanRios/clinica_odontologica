const urlTurno = `${urlApi}/turnos`;
let turnos = [];

document.addEventListener('DOMContentLoaded', async () => {
  try {
    const data = await realizarPeticion('GET', `${urlTurno}/listar`);
    if (data.length === 0) {
      contListar.innerHTML = '<p class="txt--center p--15">No hay datos disponibles.</p>';
    } else {
      listarCards(data, contListar);
      turnoLocal(data);
    }
  } catch (error) {
    console.error('Error al obtener los turnos:', error);
    contListar.innerHTML = 'Error al cargar los turnos.';
  }
});
// Eliminar
function eliminarTurno(id) {
  realizarPeticion('DELETE', `${urlTurno}/eliminar/${id}`).then(() => {
    Swal.fire(
      'Eliminado',
      'El turno ha sido eliminado correctamente.',
      'success'
    );
    return realizarPeticion('GET', `${urlTurno}/listar`);
  })
  .then(data => {
    listarCards(data, contListar);
    turnoLocal(data);
  })
  .catch(error => {
    console.error('Error al eliminar al turno: ', error);
  });
}


function turnoLocal(data) {
  turnos = data;
}

// Templates
// Card
function cardTurno(item) {
  return `
    <div class="info">
      <div class="image load">
        <img src="./assets/turno.png" alt="">
      </div>
      <div class="d-grid details g--10">
        <span class="id load"><strong>Id: ${item.id}</strong></span>
        <span class="load"><strong>Fecha y hora:</strong> ${formatearFecha(item.fechaYHora)} ${formatearHora(item.fechaYHora)}</span>
        <span class="load"><strong>Odontologo:</strong> ${item.odontologo.nombre} ${item.odontologo.apellido}</span>
        <span class="load"><strong>Paciente:</strong> ${item.paciente.nombre} ${item.paciente.apellido}</span>
        <details class="load">
          <summary>Ver más</summary>
          <details class="pl--15">
            <summary><strong><span>Odontologo:</span></strong></summary>
            <p class="pl--15">
              <span class="id"><strong>Id: ${item.odontologo.id}</strong></span>
              <br>
              <span><strong>Matricula:</strong> ${item.odontologo.matricula}</span>
            </p>
          </details>
          <details class="pl--15">
            <summary><strong><span>Paciente:</span></strong></summary>
            <p class="pl--15">
              <span class="id"><strong>Id: ${item.paciente.id}</strong></span>
              <br>
              <span><strong>DNI:</strong> ${item.paciente.dni}</span>
              <br>
              <span><strong>Fecha de ingreso:</strong> ${formatearFecha(item.paciente.fechaIngreso)}</span>
            </p>
          </details>
        </details>
      </div>
    </div>
    <div class="d-flex cont-btns g--10">
    <button class="bg--blue btn load" onclick="formActualizarTurno(${item.id})"><i class="fa fa-refresh" aria-hidden="true"></i> Actualizar</button>
    <button class="bg--red btn load" onclick="confirmarEliminar(${item.id})"><i class="fa fa-trash" aria-hidden="true"></i></button>
    </div>
  `;
}
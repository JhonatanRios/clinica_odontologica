const urlTurnos = `${urlApi}/turnos`;

document.addEventListener('DOMContentLoaded', () => {
  // Llamada a la API local para obtener todos los odontólogos
  realizarPeticion('GET', `${urlTurnos}/listar`).then(data => {
    // Mostrar los odontólogos en el div
    data.length === 0 ? contListar.innerHTML = '<p class="txt--center">No hay datos disponibles.</p>' : listarCards(data, contListar, 'turno');
  })
  .catch(error => {
    console.error('Error al obtener los turnos:', error);
    contListar.innerHTML = 'Error al cargar los turnos.';
  });
});



// Listar Todos
function crearCardTurno(item) {
  return `
    <div class="info">
      <div class="image load">
        <img src="./assets/turno.png" alt="">
      </div>
      <div class="d-grid details g--10">
        <span class="id load"><strong>Id: ${item.id}</strong></span>
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
      <button class="bg--blue btn load" onclick="modalActualizar()"><i class="fa fa-refresh" aria-hidden="true"></i> Actualizar</button>
      <button class="bg--red btn load" onclick="confirmarEliminacion()"><i class="fa fa-trash" aria-hidden="true"></i></button>
    </div>
  `;
}
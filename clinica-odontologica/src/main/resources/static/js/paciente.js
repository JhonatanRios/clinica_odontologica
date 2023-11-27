const urlPacientes = `${urlApi}/pacientes`;

document.addEventListener('DOMContentLoaded', () => {
  // Listar todos
  realizarPeticion('GET', `${urlPacientes}/listar`).then(data => {
    data.length === 0 ? contListar.innerHTML = '<p class="txt--center p--15">No hay datos disponibles.</p>' : listarCards(data, contListar);
  })
  .catch(error => {
    console.error('Error al obtener los pacientes:', error);
    contListar.innerHTML = 'Error al cargar los pacientes.';
  });
});



// Templates
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
      <button class="bg--blue btn load" onclick="modalActualizar(${item.id})"><i class="fa fa-refresh" aria-hidden="true"></i> Actualizar</button>
      <button class="bg--red btn load" onclick="confirmarEliminar(${item.id})"><i class="fa fa-trash" aria-hidden="true"></i></button>
    </div>
  `;
}
const urlPacientes = `${urlApi}/pacientes`;

document.addEventListener('DOMContentLoaded', () => {
  // Llamada a la API local para obtener todos los odontólogos
  realizarPeticion('GET', `${urlPacientes}/listar`).then(data => {
    // Mostrar los odontólogos en el div
    data.length === 0 ? contListar.innerHTML = '<p class="txt--center">No hay datos disponibles.</p>' : listarCards(data, contListar, 'paciente');
  })
  .catch(error => {
    console.error('Error al obtener los pacientes:', error);
    contListar.innerHTML = 'Error al cargar los pacientes.';
  });
});



// Listar Todos
function crearCardPaciente(item) {
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
  `;
}
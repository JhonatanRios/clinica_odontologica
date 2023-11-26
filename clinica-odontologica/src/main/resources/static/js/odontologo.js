const urlOdontologo = `${urlApi}/odontologos`;

document.addEventListener('DOMContentLoaded', () => {
  // Llamada a la API local para obtener todos los odontólogos
  realizarPeticion('GET', `${urlOdontologo}/listar`).then(data => {
    // Mostrar los odontólogos en el div
    data.length === 0 ? contListar.innerHTML = '<p class="txt--center">No hay datos disponibles.</p>' : listarCards(data, contListar, 'odontologo');
  })
  .catch(error => {
    console.error('Error al obtener los odontólogos:', error);
    contListar.innerHTML = 'Error al cargar los odontólogos.';
  });
});



// Listar Todos
function crearCardOdontologo(item) {
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
      <button class="bg--blue btn load" onclick="modalActualizar()"><i class="fa fa-refresh" aria-hidden="true"></i> Actualizar</button>
      <button class="bg--red btn load" onclick="confirmarEliminacion()"><i class="fa fa-trash" aria-hidden="true"></i></button>
    </div>
  `;
}



// Crear
function modalCrear() {
  Swal.fire({
    title: 'Registro',
    html: `
      <form id="registroForm">
        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" name="nombre" required>
        <br>
        <label for="apellido">Apellido:</label>
        <input type="text" id="apellido" name="apellido" required>
        <br>
        <label for="matricula">Matrícula:</label>
        <input type="text" id="matricula" name="matricula" required>
      </form>
    `,
    showCancelButton: true,
    confirmButtonText: 'Registrar',
    cancelButtonText: 'Cancelar',
    preConfirm: () => {
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

      // Si todo está validado, puedes hacer algo con los datos
      console.log('Nombre:', nombre);
      console.log('Apellido:', apellido);
      console.log('Matrícula:', matricula);

      return { nombre, apellido, matricula };
    }
  }).then((result) => {
    // Si el usuario hizo clic en "Registrar" y las validaciones pasaron
    if (result.isConfirmed && result.value) {
      // Aquí puedes hacer algo con los datos
      console.log('Datos registrados:', result.value);
    }
  });
}
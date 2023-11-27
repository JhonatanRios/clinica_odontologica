const urlOdontologo = `${urlApi}/odontologos`;

document.addEventListener('DOMContentLoaded', () => {
  // Listar todos
  realizarPeticion('GET', `${urlOdontologo}/listar`).then(data => {
    data.length === 0 ? contListar.innerHTML = '<p class="txt--center p--15">No hay datos disponibles.</p>' : listarCards(data, contListar);
  })
  .catch(error => {
    console.error('Error al obtener los odontólogos:', error);
    contListar.innerHTML = 'Error al cargar los odontólogos.';
  });
});
// Registrar
function registrarOdontologo(datos) {
  realizarPeticion('POST', `${urlOdontologo}/registrar`, datos).then(() => {
    return realizarPeticion('GET', `${urlOdontologo}/listar`);
  })
  .then(data => {
    listarCards(data, contListar);
  })
  .catch(error => {
    console.error('Error al registrar al odontólogo:', error);
  });
}
// Buscar por ID
function buscarPorIdOdontologo(id) {
  realizarPeticion('GET', `${urlOdontologo}/${id}`).then(data => {
    const dataArray = Array.isArray(data) ? data : [data];
    listarCards(dataArray, contListar);
  })
  .catch(error => {
    console.error('ID no encontrado:', error);
    contListar.innerHTML = `<p class="txt--center">ID #${id} no encontrado</p>`;
  });
}
// Actualizar
function modalActualizar(id) {
  console.log('clic en el item' + id);
}
// Eliminar
function eliminarOdontologo(id) {
  realizarPeticion('DELETE', `${urlOdontologo}/eliminar/${id}`)
    .then(() => {
      Swal.fire(
        'Eliminado',
        'El odontólogo ha sido eliminado correctamente.',
        'success'
      );
      return realizarPeticion('GET', `${urlOdontologo}/listar`);
    })
    .then(data => {
      // Actualizar la lista después de la eliminación
      listarCards(data, contListar);
    })
    .catch(error => {
      console.error('Error al eliminar al odontólogo: ', error);
    });
}





// Templates
// Form
function formOdontologo() {
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
      <button class="bg--blue btn load" onclick="modalActualizar(${item.id})"><i class="fa fa-refresh" aria-hidden="true"></i> Actualizar</button>
      <button class="bg--red btn load" onclick="confirmarEliminar(${item.id})"><i class="fa fa-trash" aria-hidden="true"></i></button>
    </div>
  `;
}
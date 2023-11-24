const entidades = [
  {
    imagen: './assets/odontologo.png',
    tipo: 'Odontólogo',
    link: './odontologo.html',
    descrip: 'Aqui podras registrar, listar, buscar por id, actualizar y eliminar registros'
  },
  {
    imagen: './assets/paciente.png',
    tipo: 'Paciente',
    link: './paciente.html',
    descrip: 'Aqui podras registrar, listar, buscar por id, actualizar y eliminar registros'
  },
  {
    imagen: './assets/turno.png',
    tipo: 'Turno',
    link: './turno.html',
    descrip: 'Aqui podras registrar, listar, buscar por id, actualizar y eliminar registros'
  }
]

const contCards = document.querySelector('.cont-card');

const cargarCardEntidades = () => {
  contCards.innerHTML = '';
  entidades.forEach(function (entidad) {
    let card = document.createElement('a');
    card.classList.add('d-grid', 'center', 'card', 'box', 'g--10', 'p--15');
    card.href = entidad.link;

    card.innerHTML = `
      <img class="img--entidad" src="${entidad.imagen}" alt="Imagen de ${entidad.tipo}">
      <p class="txt--center txt--gray">${entidad.tipo} <br> <span>${entidad.descrip}</span></p>
    `;

    contCards.appendChild(card);
  })
};

cargarCardEntidades();

console.log(contCards);
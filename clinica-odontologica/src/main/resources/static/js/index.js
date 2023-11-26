const entidades = [
  {
    imagen: './assets/odontologo.png',
    tipo: 'Odontólogo',
    link: './odontologo.html'
  },
  {
    imagen: './assets/paciente.png',
    tipo: 'Paciente',
    link: './paciente.html'
  },
  {
    imagen: './assets/turno.png',
    tipo: 'Turno',
    link: './turno.html'
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
      <div class="d-grid center info">
        <p class="txt--center txt--gray">${entidad.tipo}</p>
      </div>
    `;

    contCards.appendChild(card);
  })
};

cargarCardEntidades();

console.log(contCards);


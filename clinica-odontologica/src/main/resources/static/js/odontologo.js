const opciones = [
    {
      imagen: './assets/odontologo/registro.png',
      tipo: 'Registrar odontólogo',
      link: './odontologo.html',
      descrip: 'Aqui podras registrar, listar, buscar por id, actualizar y eliminar registros'
    },
    {
      imagen: './assets/odontologo/listar.png',
      tipo: 'Listar odontólogos',
      link: './odontologo.html',
      descrip: 'Aqui podras listar odontólogos'
    },
    {
      imagen: './assets/odontologo/buscar.png',
      tipo: 'Buscar por Id',
      link: './turno.html',
      descrip: 'Aqui podras buscar odontólogos por id'
    },
    {
        imagen: './assets/odontologo/eliminar.png',
        tipo: 'Actualizar y eliminar registros',
        link: './turno.html',
        descrip: 'Aqui podras actualizar y eliminar registros de odontólogos'
      }
  ]
  
  const contCards = document.querySelector('.cont-card');
  
  const cargarCardOpciones = () => {
    contCards.innerHTML = '';
    opciones.forEach(function (opcion) {
      let card = document.createElement('a');
      card.classList.add('d-grid', 'center', 'card', 'box', 'g--10', 'p--15');
      card.href = opcion.link;
  
      card.innerHTML = `
        <img class="img--entidad" src="${opcion.imagen}" alt="Imagen de ${opcion.tipo}">
        <p class="txt--center txt--gray">${opcion.tipo} <br> <span>${opcion.descrip}</span></p>
      `;
  
      contCards.appendChild(card);
    })
  };
  
  cargarCardOpciones();
  
  console.log(contCards);
  
  const listar = document.querySelector
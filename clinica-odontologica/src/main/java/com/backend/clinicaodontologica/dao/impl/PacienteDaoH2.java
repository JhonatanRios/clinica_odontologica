package com.backend.clinicaodontologica.dao.impl;

import com.backend.clinicaodontologica.dao.H2Connection;
import com.backend.clinicaodontologica.dao.IDao;
import com.backend.clinicaodontologica.model.Domicilio;
import com.backend.clinicaodontologica.model.Paciente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class PacienteDaoH2 implements IDao<Paciente> {
    private static final String INSERT = "INSERT INTO PACIENTES (NOMBRE, APELLIDO, DNI, FECHA, DOMICILIO_ID) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT = "SELECT * FROM PACIENTES";
    private static final String UPDATE = "UPDATE PACIENTES SET NOMBRE = ?, APELLIDO = ?, DNI = ?, FECHA = ?, DOMICILIO_ID = ? WHERE ID = ?";
    private final Logger LOGGER = LoggerFactory.getLogger(PacienteDaoH2.class);
    Connection conn;
    private DomicilioDaoH2 domiDaoH2;
    @Override
    public Paciente registrar(Paciente paciente) {
        conn = null;
        Paciente pacienteRegistrado = null;

        try {
            conn = H2Connection.getConnection();
            conn.setAutoCommit(false);

            domiDaoH2 = new DomicilioDaoH2();
            Domicilio domicilioRegistrado = domiDaoH2.registrar(paciente.getDomicilio());

            PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, paciente.getNombre());
            ps.setString(2, paciente.getApellido());
            ps.setInt(3, paciente.getDni());
            ps.setDate(4, Date.valueOf(paciente.getFechaIngreso()));
            ps.setInt(5, domicilioRegistrado.getId());
            ps.execute();

            pacienteRegistrado = new Paciente(paciente.getNombre(), paciente.getApellido(), paciente.getDni(), paciente.getFechaIngreso(), domicilioRegistrado);

            ResultSet rs = ps.getGeneratedKeys();
            while(rs.next()) pacienteRegistrado.setId(rs.getInt("id"));

            conn.commit();
            LOGGER.info("Se ha registrado el paciente: " + pacienteRegistrado);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                    LOGGER.info("Tuvimos un problema");
                    LOGGER.error(e.getMessage());
                    e.printStackTrace();
                } catch (SQLException exception) {
                    LOGGER.error(exception.getMessage());
                    exception.printStackTrace();
                }
            }
        } finally {
            try {
                conn.close();
            } catch (Exception ex) {
                LOGGER.error("No se pudo cerrar la conexion: " + ex.getMessage());
            }
        }

        return pacienteRegistrado;
    }

    @Override
    public List<Paciente> listarTodos() {
        conn = null;
        List<Paciente> pacientes = new ArrayList<>();
        try {
            conn = H2Connection.getConnection();
            PreparedStatement ps = conn.prepareStatement(SELECT);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Paciente paciente = crearObjetoPaciente(rs);
                pacientes.add(paciente);
            }
            LOGGER.info("Listado de todos los pacientes: " + pacientes);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (Exception ex) {
                LOGGER.error("Ha ocurrido un error al intentar cerrar la bdd. " + ex.getMessage());
                ex.printStackTrace();
            }
        }

        return pacientes;
    }

    @Override
    public Paciente buscarPorId(int id) {
        conn = null;
        Paciente paciente = null;

        try {
            conn = H2Connection.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM PACIENTES WHERE ID = ?");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                paciente = crearObjetoPaciente(rs);
            }
            if(paciente == null) LOGGER.error("No se ha encontrado el paciente con id: " + id);
            else LOGGER.info("Se ha encontrado el paciente: " + paciente);
        } catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (Exception ex){
                LOGGER.error("Ha ocurrido un error al intentar cerrar la bdd. " + ex.getMessage());
                ex.printStackTrace();
            }
        }

        return paciente;
    }

    @Override
    public Paciente actualizar(Paciente pacienteModificado) {
        conn = null;
        try {
            conn = H2Connection.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement(UPDATE);
            ps.setString(1,pacienteModificado.getNombre());
            ps.setString(2, pacienteModificado.getApellido());
            ps.setInt(3, pacienteModificado.getDni());
            ps.setDate(4, Date.valueOf(pacienteModificado.getFechaIngreso()));
            ps.setInt(5, pacienteModificado.getDomicilio().getId());
            ps.setInt(6, pacienteModificado.getId());
            ps.execute();

            conn.commit();
            LOGGER.warn("El paciente con id " + pacienteModificado.getId() + "ha sido modificado: " + pacienteModificado);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (Exception ex){
                LOGGER.error("Ha ocurrido un error al intentar cerrar la bdd. " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return pacienteModificado;
    }

    private Paciente crearObjetoPaciente(ResultSet rs) throws SQLException {
        Domicilio domicilio = new DomicilioDaoH2().buscarPorId(rs.getInt("domicilio_id"));

        return new Paciente(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido"), rs.getInt("dni"), rs.getDate("fecha").toLocalDate(), domicilio);
    }
}
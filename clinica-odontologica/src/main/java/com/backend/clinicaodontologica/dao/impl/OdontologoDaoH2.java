package com.backend.clinicaodontologica.dao.impl;

import com.backend.clinicaodontologica.dao.H2Connection;
import com.backend.clinicaodontologica.dao.IDao;
import com.backend.clinicaodontologica.model.Odontologo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OdontologoDaoH2 implements IDao<Odontologo> {
    private static final String INSERT = "INSERT INTO ODONTOLOGOS (MATRICULA, NOMBRE, APELLIDO) VALUES (?, ?, ?)";
    private static final String SELECT = "SELECT * FROM ODONTOLOGOS";
    private static final String UPDATE = "UPDATE ODONTOLOGOS SET MATRICULA = ?, NOMBRE = ?, APELLIDO = ? WHERE ID = ?";
    private static Logger LOGGER = LoggerFactory.getLogger(OdontologoDaoH2.class);
    Connection conn;

    @Override
    public Odontologo registrar(Odontologo odontologo) {
        conn = null;
        Odontologo odontologoRegistrado = null;
        try {
            conn = H2Connection.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, odontologo.getMatricula());
            ps.setString(2, odontologo.getNombre());
            ps.setString(3, odontologo.getApellido());
            ps.execute();

            odontologoRegistrado = new Odontologo(odontologo.getMatricula(), odontologo.getNombre(), odontologo.getApellido());

            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) odontologoRegistrado.setId(rs.getInt(1));

            conn.commit();
            LOGGER.info("Se ha registrado el odontologo: " + odontologoRegistrado);
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
        return odontologoRegistrado;
    }

    @Override
    public List<Odontologo> listarTodos() {
        conn = null;
        List<Odontologo> odontologos = new ArrayList<>();
        try {
            conn = H2Connection.getConnection();
            PreparedStatement ps = conn.prepareStatement(SELECT);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Odontologo odontologo = crearObjetoOdontologo(rs);
                odontologos.add(odontologo);
            }
            LOGGER.info("Listado de todos los odontologos: " + odontologos);
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
        return odontologos;
    }

    @Override
    public Odontologo buscarPorId(int id) {
        conn = null;
        Odontologo odontologo = null;
        try {
            conn = H2Connection.getConnection();
            PreparedStatement ps = conn.prepareStatement(SELECT + " WHERE ID = ?");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) odontologo = crearObjetoOdontologo(rs);
            LOGGER.info("Se ha encontrado el odontologo con id " + id + ": " + odontologo);
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
        return odontologo;
    }

    @Override
    public Odontologo actualizar(Odontologo odontologoModificado) {
        conn = null;
        try {
            conn = H2Connection.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement(UPDATE);
            ps.setString(1, odontologoModificado.getMatricula());
            ps.setString(2, odontologoModificado.getNombre());
            ps.setString(3, odontologoModificado.getApellido());
            ps.setInt(1, odontologoModificado.getId());
            ps.execute();

            conn.commit();
            LOGGER.warn("El domicilio con id " + odontologoModificado.getId() + "ha sido modificado: " + odontologoModificado);
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
        return odontologoModificado;
    }

    private Odontologo crearObjetoOdontologo (ResultSet rs) throws SQLException {
        return new Odontologo(rs.getInt("id"), rs.getString("matricula"), rs.getNString("nombre"), rs.getNString("apellido"));
    }
}

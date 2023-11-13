package com.backend.clinicaodontologica.dao.impl;

import com.backend.clinicaodontologica.dao.H2Connection;
import com.backend.clinicaodontologica.dao.IDao;
import com.backend.clinicaodontologica.model.Domicilio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DomicilioDaoH2 implements IDao<Domicilio> {
    private static final String INSERT = "INSERT INTO DOMICILIOS (CALLE, NUMERO, LOCALIDAD, PROVINCIA) VALUES (?, ?, ?, ?)";
    private static final String SELECT = "SELECT * FROM DOMICILIOS";
    private static final String UPDATE = "UPDATE PACIENTES SET CALLE = ?, NUMERO = ?, LOCALIDAD = ?, PROVINCIA = ? WHERE ID = ?";
    private final Logger LOGGER = LoggerFactory.getLogger(DomicilioDaoH2.class);
    Connection conn;
    @Override
    public Domicilio registrar(Domicilio domicilio) {
        conn = null;
        Domicilio domicilioRegistrado = null;

        try {
            conn = H2Connection.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, domicilio.getCalle());
            ps.setInt(2, domicilio.getNumero());
            ps.setString(3, domicilio.getLocalidad());
            ps.setString(4, domicilio.getProvincia());
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            domicilioRegistrado= new Domicilio(domicilio.getCalle(), domicilio.getNumero(), domicilio.getLocalidad(), domicilio.getProvincia());

            while (rs.next()){
                domicilioRegistrado.setId(rs.getInt("id"));
            }

            conn.commit();
            LOGGER.info("Se ha registrado el domicilio: " + domicilioRegistrado);
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

        return domicilioRegistrado;
    }

    @Override
    public List<Domicilio> listarTodos() {
        conn = null;
        List<Domicilio> domicilios = new ArrayList<>();

        try {
            conn = H2Connection.getConnection();
            PreparedStatement ps = conn.prepareStatement(SELECT);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Domicilio domicilio = crearObjetoDomicilio(rs);
                domicilios.add(domicilio);
            }

            LOGGER.info("Listado de domicilios obtenido: " + domicilios);
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

        return domicilios;
    }

    @Override
    public Domicilio buscarPorId(int id) {
        Domicilio domicilio = null;
        conn = null;

        try {
            conn =H2Connection.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM DOMICILIOS WHERE ID = ?");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                domicilio = new Domicilio(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5));
            }

            if(domicilio == null) LOGGER.error("No se ha encontrado el domicilio con id: " + id);
            else LOGGER.info("Se ha encontrado el domicilio: " + domicilio);
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

        return domicilio;
    }

    @Override
    public Domicilio actualizar(Domicilio domicilioModificado) {
        conn = null;
        try {
            conn = H2Connection.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement(UPDATE);
            ps.setString(1, domicilioModificado.getCalle());
            ps.setInt(2, domicilioModificado.getNumero());
            ps.setString(3, domicilioModificado.getLocalidad());
            ps.setString(4, domicilioModificado.getProvincia());
            ps.setInt(6, domicilioModificado.getId());
            ps.execute();

            conn.commit();
            LOGGER.warn("El domicilio con id " + domicilioModificado.getId() + "ha sido modificado: " + domicilioModificado);
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
        return domicilioModificado;
    }

    private Domicilio crearObjetoDomicilio (ResultSet rs) throws SQLException {
        return new Domicilio(rs.getInt("id"), rs.getString("calle"), rs.getInt("numero"),rs.getString("localidad"), rs.getString("provincia"));
    }
}
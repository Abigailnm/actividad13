/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class ModelAgenda {

    private Connection conexion;
    private Statement st;
    private ResultSet rs;

    private String nombre;
    private String email;
    private int id;

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Método que realiza las siguietnes acciones:
     * 1.- Conecta con la base agenda_mvc.
     * 2.- Consulta todo los registros de la tabla contactos.
     * 3.- Obtiene el nombre y el email y los guarda en las variables globales
     * nombre y email.
     */
    public void conectarDB() {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/agenda_mvc", "user_mvc", "pass_mvc.2018");
            st = conexion.createStatement();
            rs = st.executeQuery("SELECT * FROM contactos;");
            rs.next();
            nombre = rs.getString("nombre");
            email = rs.getString("email");
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, "Error ModelAgenda 001: " + err.getMessage());
        }
    }
    
    /**
     * Método que realiza las siguiente acciones:
     * 1.- Moverse al primer registro
     * 2.- obtener el valor del nombre de rs y guardarlo en la variable nombre
     * 3.- obtener el valor del email de rs y guardarlo en la variable email
     */
    public void moverPrimerRegistro(){
        try {
            if (rs.isFirst() == false) {
                rs.first(); //Primer registro

                this.setNombre(rs.getString("nombre"));
                this.setEmail(rs.getString("email"));
            }
        } catch (Exception err) {
            JOptionPane.showMessageDialog(null, "Error " + err.getMessage());
        }
    }
    
    /**
     * Método que realiza las siguiente acciones:
     * 1.- Moverse al anterior registro
     * 2.- obtener el valor del nombre de rs y guardarlo en la variable nombre
     * 3.- obtener el valor del email de rs y guardarlo en la variable email
     */
    public void moverAnteriorRegistro(){
        try {
            if (rs.isFirst() == false) {
                rs.previous(); //Registro anterior

                this.setNombre(rs.getString("nombre"));
                this.setEmail(rs.getString("email"));
            }
        } catch (Exception err) {
            JOptionPane.showMessageDialog(null, "Error " + err.getMessage());
        }
    }
    
    /**
     * Método que realiza las siguiente acciones:
     * 1.- Moverse al siguiente registro
     * 2.- obtener el valor del nombre de rs y guardarlo en la variable nombre
     * 3.- obtener el valor del email de rs y guardarlo en la variable email
     */
    public void moverSiguienteRegistro(){
        try{
            if(rs.isLast()==false) {
                rs.next(); // Siguiente registro 
                
                this.setNombre(rs.getString("nombre"));
                this.setEmail(rs.getString("email"));
            }
        } catch(Exception err) {
            JOptionPane.showMessageDialog(null,"Error " + err.getMessage());
        }
    }
    
    /**
     * Método que realiza las siguiente acciones:
     * 1.- Moverse al ultimo registro
     * 2.- obtener el valor del nombre de rs y guardarlo en la variable nombre
     * 3.- obtener el valor del email de rs y guardarlo en la variable email
     */
    public void moverUltimoRegistro(){
        try {
            if (rs.isLast() == false) {
                rs.last(); //Último registro

                this.setNombre(rs.getString("nombre"));
                this.setEmail(rs.getString("email"));
            }
        } catch (Exception err) {
            JOptionPane.showMessageDialog(null, "Error " + err.getMessage());
        }
    }
      /**
     * Método que realiza las siguiente acciones:
     *  connfirmacion de eliminar 
     
     */
    public void eliRegistro() {
        try {
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar este registro?", "Borrar", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                id = rs.getInt("id_contacto");
                st.executeUpdate("DELETE FROM contactos WHERE id_contacto = "+ id +"; ");
                
                this.conectarDB();
                this.moverUltimoRegistro();
            }
            else {
                this.conectarDB();
                this.moverUltimoRegistro();
            }
        }
        catch(SQLException err) { 
            JOptionPane.showMessageDialog(null,"Error en metodo eliminar"+err.getMessage()); 
        }
    }
    

    
    /**
     * Método para crear un nuevo registro y poder almacenarlo
     
     */
    public void insertarRegistro() {
        try {
            nombre = this.getNombre();
            email = this.getEmail();
            st.executeUpdate("INSERT INTO contactos (nombre, email)" + " VALUES ('"+ nombre +"','"+ email +"');");
            JOptionPane.showMessageDialog(null, "Felicidades registro guardado.");
            this.conectarDB();
            this.moverUltimoRegistro();
        }
        catch(SQLException err) { 
            JOptionPane.showMessageDialog(null,"Error "+err.getMessage()); 
        }
    }
    
    /**
     * Método para guardar cambios 
     */
    public void modiRegistro() {
        try {
            id = rs.getInt("id_contacto");
            nombre = this.getNombre();
            email = this.getEmail();
            st.executeUpdate("UPDATE contactos SET nombre = '"+ nombre +"', email = '"+ email +"' WHERE id_contacto = "+ id +"; ");
            JOptionPane.showMessageDialog(null, "Se modifico correctamente el registro.");
            this.conectarDB();
            this.moverUltimoRegistro();
        }
        catch(SQLException err) { 
            JOptionPane.showMessageDialog(null,"Error "+err.getMessage()); 
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univates.paa.kickquiz.DAO;

import br.univates.paa.kickquiz.model.Usuario;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.List;
import org.hibernate.Query;

/**
 *
 * @author Avell G1310 MAX
 */
public class UsuarioDAO extends ModelDAO<Usuario> {

    @Override
    public Class getObject() {
        return Usuario.class;
    }

    public Usuario checkLogin(Usuario u) {
        session.beginTransaction();
        Query query = session.createQuery("from Usuario where login = :login and senha = :senha");
        query.setParameter("login", u.getLogin());
        query.setParameter("senha", encryptPassword(u.getSenha()));
        List list = query.list();
        session.getTransaction().commit();
        return list.size() == 1 ? (Usuario) list.get(0) : null;
    }

    private String encryptPassword(String password) {
        String sha1 = "";
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sha1;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}

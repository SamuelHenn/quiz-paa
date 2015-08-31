package br.univates.paa.kickquiz;

import br.univates.paa.kickquiz.model.Usuario;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FXMLController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private TextField nome;

    @FXML
    private void handleButtonAction(ActionEvent event) {

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Usuario user = new Usuario();
        user.setNome(nome.getText());
        session.save(user);

        session.getTransaction().commit();

        Query query = session.createQuery("from Usuario");
        List<Usuario> list = query.list();

        String users = "";
        Iterator<Usuario> itr = list.iterator();
        while (itr.hasNext()) {
            Usuario q = itr.next();
            
            users += "\n" + q.getNome();
        }
        session.close();

        label.setText("Hello World!" + users);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        Query query = session.createQuery("from Usuario");
        List<Usuario> list = query.list();

        String users = "";
        Iterator<Usuario> itr = list.iterator();
        while (itr.hasNext()) {
            Usuario q = itr.next();
            
            users += "\n" + q.getNome();
        }
        session.close();
    }
}

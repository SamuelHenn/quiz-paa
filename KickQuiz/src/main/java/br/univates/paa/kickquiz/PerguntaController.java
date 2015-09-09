package br.univates.paa.kickquiz;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class PerguntaController implements Initializable {

    @FXML
    private void btnStart(ActionEvent event) {

        /*SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
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

        label.setText("Hello World!" + users);*/
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}

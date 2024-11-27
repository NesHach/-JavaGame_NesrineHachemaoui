import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RenderEngine extends JPanel implements Engine {

    private ArrayList<Displayable> renderList;


    public RenderEngine(JFrame jFrame) {
        renderList = new ArrayList<>(); // Initialisation de la liste
    }

    // Ajoute un seul objet à afficher
    public void addToRenderList(Displayable displayable) {
        synchronized (renderList) { // Synchronisation pour éviter les conflits
            if (!renderList.contains(displayable)) {
                renderList.add(displayable);
            }
        }
    }

    // Ajoute une liste d'objets à afficher
    public void addToRenderList(ArrayList<Displayable> displayable) {
        synchronized (renderList) { // Synchronisation pour éviter les conflits
            for (Displayable item : displayable) {
                if (!renderList.contains(item)) {
                    renderList.add(item);
                }
            }
        }
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        synchronized (renderList) {
            for (Displayable renderObject : renderList) {
                renderObject.draw(g); // Dessine chaque objet

                // Si l'objet est un DynamicSprite, affiche la barre de vie
                if (renderObject instanceof DynamicSprite) {
                    ((DynamicSprite) renderObject).drawHealthBar(g);
                }
            }
        }
    }

    // Met à jour l'affichage
    @Override
    public void update() {
        this.repaint(); // Rafraîchit l'écran
    }
}
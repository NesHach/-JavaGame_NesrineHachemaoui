import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main {

    JFrame displayZoneFrame;

    RenderEngine renderEngine;
    GameEngine gameEngine;
    PhysicEngine physicEngine;

    public Main() throws Exception {
        // Initialisation de la fenêtre principale
        displayZoneFrame = new JFrame("Java Labs");
        displayZoneFrame.setSize(1600, 900);
        displayZoneFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Initialisation des moteurs
        renderEngine = new RenderEngine(displayZoneFrame);
        physicEngine = new PhysicEngine();

        // Initialisation du héros
        DynamicSprite hero = new DynamicSprite(100, 200,
                ImageIO.read(new File("./img/heroTileSheetLowRes.png")), 48, 50);

        // Ajout du niveau en premier (il doit être en arrière-plan)
        Playground level = new Playground("./data/level1.txt");
        renderEngine.addToRenderList(level.getSpriteList());
        physicEngine.setEnvironment(level.getSolidSpriteList());

        // Ajout des pièges
        SolidSprite trap1 = new SolidSprite(100, 100,
                ImageIO.read(new File("./img/trap.png")), 30, 30);
        renderEngine.addToRenderList(trap1);
        physicEngine.addToTraps(trap1);

        SolidSprite trap2 = new SolidSprite(300, 300,
                ImageIO.read(new File("./img/trap.png")), 30, 30);
        renderEngine.addToRenderList(trap2);
        physicEngine.addToTraps(trap2);

        SolidSprite trap3 = new SolidSprite(200, 200,
                ImageIO.read(new File("./img/trap.png")), 30, 30);
        renderEngine.addToRenderList(trap3);
        physicEngine.addToTraps(trap3);

        SolidSprite trap4 = new SolidSprite(400, 300,
                ImageIO.read(new File("./img/trap.png")), 30, 30);
        renderEngine.addToRenderList(trap4);
        physicEngine.addToTraps(trap4);

        SolidSprite trap5 = new SolidSprite(500, 200,
                ImageIO.read(new File("./img/trap.png")), 30, 30);
        renderEngine.addToRenderList(trap5);
        physicEngine.addToTraps(trap5);

        SolidSprite trap6 = new SolidSprite(700, 400,
                ImageIO.read(new File("./img/trap.png")), 30, 30);
        renderEngine.addToRenderList(trap6);
        physicEngine.addToTraps(trap6);

        SolidSprite trap7 = new SolidSprite(700, 100,
                ImageIO.read(new File("./img/trap.png")), 30, 30);
        renderEngine.addToRenderList(trap7);
        physicEngine.addToTraps(trap7);

        SolidSprite trap8 = new SolidSprite(850, 200,
                ImageIO.read(new File("./img/trap.png")), 30, 30);
        renderEngine.addToRenderList(trap8);
        physicEngine.addToTraps(trap8);

        // Ajout du héros en dernier (pour qu'il soit au premier plan)
        renderEngine.addToRenderList(hero);
        physicEngine.addToMovingSpriteList(hero);

        // Initialisation de la logique du jeu
        gameEngine = new GameEngine(hero);

        // Timers pour les mises à jour
        Timer renderTimer = new Timer(50, (time) -> renderEngine.update());
        Timer gameTimer = new Timer(50, (time) -> gameEngine.update());
        Timer physicTimer = new Timer(50, (time) -> {
            physicEngine.update(); // Mise à jour physique
            checkHeroHealth(hero); // Vérifie la santé du héros
        });

        renderTimer.start();
        gameTimer.start();
        physicTimer.start();

        // Ajout de la fenêtre au moteur de rendu
        displayZoneFrame.getContentPane().add(renderEngine);
        displayZoneFrame.setVisible(true);

        // Ajout du KeyListener
        displayZoneFrame.addKeyListener(gameEngine);
    }

    //Vérifie la santé du héros et déclenche le Game Over si elle est à zéro.
    private void checkHeroHealth(DynamicSprite hero) {
        if (hero.getHealth() <= 0) { // Si la santé du héros est à zéro ou moins
            triggerGameOver();
        }
    }

    //Game over
    private void triggerGameOver() {
        System.out.println("Game Over! Le héros a perdu tous ses points de vie.");

        // Affiche une boîte de dialogue Swing
        JOptionPane.showMessageDialog(displayZoneFrame, "Game Over! Vous avez perdu.", "Game Over", JOptionPane.INFORMATION_MESSAGE);

        // Ferme l'application
        System.exit(0); // Quitte le programme
    }

    public static void main(String[] args) throws Exception {
        new Main();
    }
}
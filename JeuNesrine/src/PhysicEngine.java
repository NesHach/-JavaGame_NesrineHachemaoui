import java.lang.reflect.Array;
import java.util.ArrayList;

public class PhysicEngine implements Engine{
    private ArrayList<DynamicSprite> movingSpriteList;
    private ArrayList <Sprite> environment;
    private ArrayList<SolidSprite> traps; // Liste pour qu'on puisse stocker les pièges.
    public PhysicEngine() {
        movingSpriteList = new ArrayList<>();
        environment = new ArrayList<>();
        traps = new ArrayList<>(); // On initialise la liste des pièges.
    }

    public void addToEnvironmentList(Sprite sprite){
        if (!environment.contains(sprite)){
            environment.add(sprite);
        }
    }

    public void setEnvironment(ArrayList<Sprite> environment){
        this.environment=environment;
    }


    public void checkTraps(DynamicSprite hero) {
        for (SolidSprite trap : traps) {
            // Vérifie si la hitbox du piège intersecte la hitbox du héros.
            if (trap.getHitBox().intersects(hero.getHitBox())) {
                // Si une collision est détectée, diminue la vie du héros de 10 points.
                hero.decreaseHealth(10);
                System.out.println("Collision avec un piège ! Vie restante : " + hero.getHealth());
            }
        }
    } //Vérifie si le héros entre en collision avec un piège dans la liste des pièges.
    public void addToMovingSpriteList(DynamicSprite sprite){
        if (!movingSpriteList.contains(sprite)){
            movingSpriteList.add(sprite);
        }
    }
    public void addToTraps(SolidSprite trap) {
        traps.add(trap);
    } //methode pour pouvoir ajouter les pièges dans la liste pour qu'il soit pris en compte dans la détéction de collision
    @Override
    public void update() {
        for(DynamicSprite dynamicSprite : movingSpriteList){
            dynamicSprite.moveIfPossible(environment);
            checkTraps(dynamicSprite);
            // Vérifie si le héros entre en collision avec un piège.

        }
    }
}

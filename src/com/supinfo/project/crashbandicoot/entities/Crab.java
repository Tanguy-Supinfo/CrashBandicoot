package com.supinfo.project.crashbandicoot.entities;

import com.supinfo.project.crashbandicoot.game.Level;
import com.supinfo.project.crashbandicoot.graphics.Colors;
import com.supinfo.project.crashbandicoot.graphics.Renderer;
import com.supinfo.project.crashbandicoot.graphics.Texture;
import com.supinfo.project.crashbandicoot.utiles.Delay;

public class Crab extends Entity {

    int level;

    boolean isEnabled = true;

    private int length;
    private int speed;
    private int time;
    private int coord;

    private int dir;
    private int dirCount;

    boolean invert = false;

    Delay delay;

    // constructeur de la classe
    public Crab(int x, int y, int level) {
        super(x, y);

        this.level = level;

        texture = Texture.crab;
    }

    // initialisation des paramètres lors de l'initialisation de l'objet
    @Override
    public void init(Level level) {
        length = 60;
        speed = 2;
        time = 0;
        coord = 0;

        dir = 0;
        dirCount = 0;

        delay = new Delay(2, true);
    }

    // Update frame par frame des valeur de variable
    @Override
    public void update() {
        if(isEnabled && Level.levelNumber == level) {
            if (true) {
                time++;
                if (time > speed) {
                    if (coord <= length && invert == false) coord++;
                    if(coord <= length && invert == true) coord--;
                    if (coord == length) invert = true;
                    if (coord == 0) invert = false;

                    if(dirCount > 5) {
                        dirCount = 0;
                        if(dir == 0) dir = 1;
                        else dir = 0;
                    }

                    dirCount++;

                    time = 0;
                }
            }

            // détecteur de collision
            if ((Player.playerX+10 >= x + coord + 35)
                    || (Player.playerX+10 + Player.playerBoxWidth-20 <= x + coord)
                    || (Player.playerY+10 >= y + 35)
                    || (Player.playerY+10 + Player.playerBoxHeight-20 <= y)){
                // Le joueur n'est pas dans la zone
            } else {
                if (Player.tornadoAttack == false && delay.talk() == true) {
                    // le joueur meurt
                    Player.killPlayer = true;
                    System.out.println("[Player] was killed by Crab !");
                    delay.start();
                } else if (Player.tornadoAttack == true && delay.talk() == true) {
                    isEnabled = false;
                }

            }

        }
    }

    // méthode de rendu graphique frame par frame
    @Override
    public void render() {
        if(isEnabled && Level.levelFinished != true && Level.levelNumber == level) {
            texture.bind();
                Renderer.renderEntity(x + coord, y, 25, 25, Colors.WHITE, 2f, dir, 0);
            texture.unbind();
        }
    }

    // getter and setter

    public int getLevel() { return level; }

    public boolean getEnabled() { return isEnabled; }

    public void setEnabled(boolean value) { isEnabled = value; }

}

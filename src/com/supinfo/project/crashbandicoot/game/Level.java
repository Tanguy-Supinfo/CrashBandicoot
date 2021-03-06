package com.supinfo.project.crashbandicoot.game;

import com.supinfo.project.crashbandicoot.Component;
import com.supinfo.project.crashbandicoot.entities.*;
import com.supinfo.project.crashbandicoot.game.tiles.Tile;
import com.supinfo.project.crashbandicoot.graphics.Colors;
import com.supinfo.project.crashbandicoot.graphics.Header;
import com.supinfo.project.crashbandicoot.graphics.Renderer;
import com.supinfo.project.crashbandicoot.graphics.Texture;
import com.supinfo.project.crashbandicoot.objects.Boxes;
import com.supinfo.project.crashbandicoot.objects.CheckPoint;
import com.supinfo.project.crashbandicoot.objects.Clouds;
import com.supinfo.project.crashbandicoot.objects.Fruit;
import com.supinfo.project.crashbandicoot.utiles.AudioControl;
import com.supinfo.project.crashbandicoot.utiles.ObjectsAnimation;
import com.supinfo.project.crashbandicoot.utiles.ScreenLoader;
import com.supinfo.project.crashbandicoot.utiles.levelSound.SoundLvl1;
import com.supinfo.project.crashbandicoot.utiles.levelSound.SoundLvl2;
import com.supinfo.project.crashbandicoot.utiles.levelSound.SoundLvl3;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Level {

    // Attention aux yeux, dans cette classe on aime bien ce répéter (tu vas vite comprendre en la parcourant) !

    public float gravity = 1.8f;

    public int width, height;

    Tile[][] solidTile;

    List<Tile> tiles = new ArrayList<Tile>();

    List<Entity> entities = new ArrayList<>();

    public static boolean levelFinished = false;
    public static int levelNumber = 1;

    Texture textureFruit;

    public static ArrayList<Fruit> fruits = new ArrayList<>();
    public static ArrayList<Boxes> boxes = new ArrayList<Boxes>();
    public static ArrayList<CheckPoint> checkpoints = new ArrayList<CheckPoint>();
    public static ArrayList<Clouds> clouds = new ArrayList<Clouds>();

    ArrayList<Crab> crabs = new ArrayList<>();
    ArrayList<Fish> fishs = new ArrayList<>();
    ArrayList<Plant> plants = new ArrayList<>();

    ArrayList<Traps> traps = new ArrayList<>();

    private static Player player = new Player(10, 80);
    public static AkuAku akuaku = new AkuAku(8, 90);

    ObjectsAnimation animFruits;

    public static AudioControl wompasSound;
    public static SoundLvl1 lvl1Sound;
    public static SoundLvl2 lvl2Sound;
    public static SoundLvl3 lvl3Sound;

    public static int loadLevel = 0;

    // constructeur de la classe Level
    public Level(int width, int height) {
        this.width = width;
        this.height = height;

        generate();
        spawner();

        animFruits = new ObjectsAnimation(5, 6, true);

        lvl1Sound = new SoundLvl1();
        lvl2Sound = new SoundLvl2();
        lvl3Sound = new SoundLvl3();
    }

    // fonction de génération des tuiles du terrain pour chaque niveau
    public void generate() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles.add(new Tile(x, y, x, y, Tile.Tiles.BG));
            }
        }
    }

    // Voila de quoi ajouter le joueur et Akuaku sur le terrain (sinon on s'amuse moins c'est sur :) )
    public void spawner() {
        player.init(this);
        akuaku.init(this);

        addEntity(player);
        addEntity(akuaku);
    }

    // fonction de récupération des tuiles pour en connaitre leurs positions
    public Tile getSolidTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) return null;
        return solidTile[x][y];
    }

    // fonction d'initialisation des composants essentiels aux niveaux
    public void init() {
        solidTile = new Tile[width][height];
        textureFruit = Texture.apple;

        initObjects();
        initEntities();
        animFruits.play();

        Header.init();

        wompasSound = new AudioControl();

        ScreenLoader.init();

        clouds.clear();
        clouds.add(new Clouds(-70, 30, 4, true));
        clouds.add(new Clouds(40, 10, 4, true));
        clouds.add(new Clouds(300, 45, 4, true));
        clouds.add(new Clouds(550, 10, 4, true));
        clouds.add(new Clouds(800, 15, 4, true));
        clouds.add(new Clouds(950, 45, 4, true));

        /*clouds.add(new Clouds(1050, 15, 4, false));
        clouds.add(new Clouds(1200, 10, 4, false));
        clouds.add(new Clouds(1350, 30, 4, false));
        clouds.add(new Clouds(1500, 30, 4, false));
        clouds.add(new Clouds(1680, 45, 4, false));
        clouds.add(new Clouds(1800, 10, 4, false));*/
    }

    // Initialisation des tiles map par map (du moins niveau par niveau) car 1 map = 1 niveau chez nous
    public void mapInit() {
        solidTile = new Tile[width][height];
        System.out.println("Map Init ! Level : " + levelNumber);
        for (int i = 0; i < width; i++) {
            for (int j = 7; j < 15; j++) {
                if(Level.levelNumber == 1 && i != 28 && i != 29 /* && i != 28 && i != 48 && i != 49*/) {
                    solidTile[i][j] = new Tile(i, j, 0, 0, Tile.Tiles.COL);
                } else if(Level.levelNumber == 2 && i != 23 && i != 24 && i != 34 && i != 35 && i != 72 && i != 73){
                    solidTile[i][j] = new Tile(i, j, 0, 0, Tile.Tiles.COL);
                } else if(Level.levelNumber == 3){
                    solidTile[i][j] = new Tile(i, j, 0, 0, Tile.Tiles.COL);
                }
            }
        }

        // Attention aux yeux :
        if(Level.levelNumber == 1) {
            solidTile[16][5] = new Tile(15, 5, 0, 0, Tile.Tiles.COL);
            solidTile[17][5] = new Tile(15, 5, 0, 0, Tile.Tiles.COL);
            solidTile[18][5] = new Tile(15, 5, 0, 0, Tile.Tiles.COL);
        } else if(Level.levelNumber == 2) {
            solidTile[29][5] = new Tile(29, 5, 0, 0, Tile.Tiles.COL);
            solidTile[30][5] = new Tile(30, 5, 0, 0, Tile.Tiles.COL);
            solidTile[31][5] = new Tile(31, 5, 0, 0, Tile.Tiles.COL);

            solidTile[62][5] = new Tile(62, 5, 0, 0, Tile.Tiles.COL);
            solidTile[63][5] = new Tile(63, 5, 0, 0, Tile.Tiles.COL);
            solidTile[64][5] = new Tile(64, 5, 0, 0, Tile.Tiles.COL);

            solidTile[95][5] = new Tile(95, 5, 0, 0, Tile.Tiles.COL);
            solidTile[96][5] = new Tile(96, 5, 0, 0, Tile.Tiles.COL);
            solidTile[97][5] = new Tile(97, 5, 0, 0, Tile.Tiles.COL);

            solidTile[97][3] = new Tile(97, 3, 0, 0, Tile.Tiles.COL);
            solidTile[98][3] = new Tile(98, 3, 0, 0, Tile.Tiles.COL);
            solidTile[99][3] = new Tile(99, 3, 0, 0, Tile.Tiles.COL);
        } else if(Level.levelNumber == 3) {
            solidTile[27][5] = new Tile(27, 5, 0, 0, Tile.Tiles.COL);
            solidTile[28][5] = new Tile(28, 5, 0, 0, Tile.Tiles.COL);
            solidTile[29][5] = new Tile(29, 5, 0, 0, Tile.Tiles.COL);

            solidTile[29][3] = new Tile(29, 3, 0, 0, Tile.Tiles.COL);
            solidTile[30][3] = new Tile(30, 3, 0, 0, Tile.Tiles.COL);
            solidTile[31][3] = new Tile(31, 3, 0, 0, Tile.Tiles.COL);

            solidTile[79][5] = new Tile(79, 5, 0, 0, Tile.Tiles.COL);
            solidTile[80][5] = new Tile(80, 5, 0, 0, Tile.Tiles.COL);
            solidTile[81][5] = new Tile(81, 5, 0, 0, Tile.Tiles.COL);

            solidTile[82][3] = new Tile(82, 3, 0, 0, Tile.Tiles.COL);
            solidTile[83][3] = new Tile(83, 3, 0, 0, Tile.Tiles.COL);
            solidTile[34][3] = new Tile(84, 3, 0, 0, Tile.Tiles.COL);

        }
    }

    // ici on ajoute une entité au niveau
    public void addEntity(Entity e) {
        entities.add(e);
    }

    // et la bas ... on la supprime !
    public void removeEntity(Entity e) {
        entities.remove(e);
    }

    // cette fonction nous permet d'update les valeur de chaques objets un à un
    public void update() {
        for(int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            if (e.getRemoved()) entities.remove(e);
            e.update();
        }

        for (int i = 0; i < traps.size(); i++) {
            traps.get(i).update();
        }

        for (int i = 0; i < boxes.size(); i++) {
            if(boxes.get(i).getBreak() != true)
                boxes.get(i).update();
        }

        for (int i = 0; i < checkpoints.size(); i++) {
            checkpoints.get(i).update();
        }

        animFruits.update();

        for (int i = 0; i < clouds.size(); i++) {
            if(levelNumber == 1) {
                if(clouds.get(i).getLevel1() == true) clouds.get(i).update();
            } else {
                clouds.get(i).update();
            }
        }

        if(loadLevel < Level.levelNumber) {
            System.out.println("Cond : " + loadLevel);
            loadLevel = Level.levelNumber;
            mapInit();
            initObjects();
        }
    }

    // maintenant que tout cela est fait, on peut rendre nos composants de niveau frame après frame
    public void render() {
        for (Tile tile : tiles) {
            tile.render();
        }
        for(int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            e.render();
        }

        ScreenLoader.render();

        if(levelFinished == true) Renderer.renderBlackOut(1);
        if(Player.playerLife == 0) Renderer.renderBlackOut(2);
    }

    // Alors ici c'est quelque chose ! On se répéte beaucoup mais il le faut bien ..
    // C'est ici que nous créons et initialison chaques objets comme les caisses, les fruits, les checkpoints ...
    // et le tout niveau par niveau
    public static void initObjects() {
        fruits.clear();
        boxes.clear();
        checkpoints.clear();

        if(Level.levelNumber == 1) {
            fruits.add(new Fruit(70, 120, false, 1));
            fruits.add(new Fruit(100, 90, false, 1));
            fruits.add(new Fruit(130, 120, false, 1));
            fruits.add(new Fruit(300, 45, false, 1));
            fruits.add(new Fruit(320, 25, false, 1));
            //fruits.add(new Fruit(340, 5, false, 1));
            fruits.add(new Fruit(445, 95, false, 1));
            fruits.add(new Fruit(470, 80, false, 1));
            fruits.add(new Fruit(495, 95, false, 1));
            fruits.add(new Fruit(735, 80, false, 1));
            fruits.add(new Fruit(805, 80, false, 1));
            fruits.add(new Fruit(870, 105, false, 1));
            fruits.add(new Fruit(925, 80, false, 1));
            fruits.add(new Fruit(925, 50, false, 1));
            fruits.add(new Fruit(925, 20, false, 1));

            boxes.add(new Boxes(250, 130,0,true,false, Boxes.BoxType.BASIC,1));
            boxes.add(new Boxes(280, 100,0,false,false, Boxes.BoxType.IRON,1));
            boxes.add(new Boxes(380, 130,0,true,false, Boxes.BoxType.AKU,1));
            boxes.add(new Boxes(540, 130,1,true,false, Boxes.BoxType.BASIC,1));
            boxes.add(new Boxes(660, 130,1,false,false, Boxes.BoxType.TNT,1));
            boxes.add(new Boxes(800, 130,0,true,false, Boxes.BoxType.BASIC,1));
            boxes.add(new Boxes(920, 130,500,false,false, Boxes.BoxType.ARROW,1));

            checkpoints.add(new CheckPoint(425, 121, false, 1));

        } else if(Level.levelNumber == 2) {
            fruits.add(new Fruit(100, 100, false, 2));
            fruits.add(new Fruit(130, 100, false, 2));
            fruits.add(new Fruit(160, 100, false, 2));
            fruits.add(new Fruit(455, 90, false, 2));
            fruits.add(new Fruit(495, 60, false, 2));
            fruits.add(new Fruit(725, 100, false, 2));
            fruits.add(new Fruit(845, 100, false, 2));
            fruits.add(new Fruit(875, 100, false, 2));
            fruits.add(new Fruit(905, 100, false, 2));
            fruits.add(new Fruit(1260, 100, false, 2));
            fruits.add(new Fruit(1290, 100, false, 2));
            fruits.add(new Fruit(1750, 110, false, 2));
            fruits.add(new Fruit(1780, 100, false, 2));
            fruits.add(new Fruit(1810, 110, false, 2));
            fruits.add(new Fruit(1840, 120, false, 2));
            fruits.add(new Fruit(1870, 110, false, 2));
            fruits.add(new Fruit(1900, 100, false, 2));

            boxes.add(new Boxes(450, 130,0,false,false, Boxes.BoxType.IRON,2));
            boxes.add(new Boxes(490, 100,0,false,false, Boxes.BoxType.IRON,2));
            boxes.add(new Boxes(780, 130,0,true,false, Boxes.BoxType.CRASH,2));
            boxes.add(new Boxes(970, 130,1,false,false, Boxes.BoxType.TNT,2));
            boxes.add(new Boxes(1010, 100,10,true,false, Boxes.BoxType.JUMP,2));
            boxes.add(new Boxes(1340, 130,0,true,false, Boxes.BoxType.AKU,2));
            boxes.add(new Boxes(1460, 130,1,false,false, Boxes.BoxType.NITRO,2));
            boxes.add(new Boxes(1510, 130,0,false,false, Boxes.BoxType.IRON,2));
            //boxes.add(new Boxes(1540, 100,10,true,false, Boxes.BoxType.JUMP,2));
            boxes.add(new Boxes(1540, 100,0,false,false, Boxes.BoxType.IRON,2));
            boxes.add(new Boxes(1570, 70,0,false,false, Boxes.BoxType.IRON,2));
            //boxes.add(new Boxes(1600, 40,10,true,false, Boxes.BoxType.JUMP,2));
            boxes.add(new Boxes(1690, 130,1,false,false, Boxes.BoxType.TNT,2));

            checkpoints.add(new CheckPoint(1280, 125, false, 2));
        } else if(Level.levelNumber == 3) {
            fruits.add(new Fruit(70, 120, false, 3));
            fruits.add(new Fruit(100, 90, false, 3));
            fruits.add(new Fruit(130, 120, false, 3));
            fruits.add(new Fruit(495, 40, false, 3));
            fruits.add(new Fruit(525, 40, false, 3));
            fruits.add(new Fruit(555, 70, false, 3));
            fruits.add(new Fruit(585, 100, false, 3));
            fruits.add(new Fruit(685, 20, false, 3));
            fruits.add(new Fruit(910, 120, false, 3));
            fruits.add(new Fruit(940, 90, false, 3));
            fruits.add(new Fruit(970, 120, false, 3));
            fruits.add(new Fruit(1090, 70, false, 3));
            fruits.add(new Fruit(1090, 100, false, 3));
            fruits.add(new Fruit(1090, 130, false, 3));
            fruits.add(new Fruit(1490, 130, false, 3));
            fruits.add(new Fruit(1540, 100, false, 3));
            fruits.add(new Fruit(1590, 70, false, 3));
            fruits.add(new Fruit(1610, 120, false, 3));
            fruits.add(new Fruit(1630, 70, false, 3));
            fruits.add(new Fruit(1680, 100, false, 3));
            fruits.add(new Fruit(1730, 130, false, 3));

            boxes.add(new Boxes(350, 130,0,true,false, Boxes.BoxType.AKU,3));
            boxes.add(new Boxes(410, 130,0,false,false, Boxes.BoxType.IRON,3));
            boxes.add(new Boxes(450, 100,0,false,false, Boxes.BoxType.IRON,3));
            boxes.add(new Boxes(490, 70,0,false,false, Boxes.BoxType.IRON,3));
            boxes.add(new Boxes(580, 130,1,false,false, Boxes.BoxType.TNT,3));
            boxes.add(new Boxes(630, 130,500,false,false, Boxes.BoxType.ARROW,3));
            boxes.add(new Boxes(680, 60,0,true,false, Boxes.BoxType.BASIC,3));
            boxes.add(new Boxes(1020, 130,0,true,false, Boxes.BoxType.AKU,3));
            boxes.add(new Boxes(1160, 130,1,false,false, Boxes.BoxType.NITRO,3));
            boxes.add(new Boxes(1230, 130,0,false,false, Boxes.BoxType.IRON,3));
            boxes.add(new Boxes(1280, 100,0,false,false, Boxes.BoxType.IRON,3));
            boxes.add(new Boxes(1330, 70,0,false,false, Boxes.BoxType.IRON,3));

            checkpoints.add(new CheckPoint(1320, 115, false, 3));
        }
    }

    // Ici même principe qu'au dessus mais pour les entités (= ennemies)
    public void initEntities() {
        // Level 1
        crabs.add(new Crab(160, 135, 1));
        crabs.add(new Crab(280, 135, 1));
        // Level 2
        crabs.add(new Crab(100, 135, 2));
        crabs.add(new Crab(1070, 135, 2));
        crabs.add(new Crab(1580, 135, 2));
        // Level 3
        crabs.add(new Crab(250, 135, 3));
        crabs.add(new Crab(740, 135, 3));
        crabs.add(new Crab(1800, 135, 3));

        // Level 1
        fishs.add(new Fish(733, 150, 1));
        // Level 3
        fishs.add(new Fish(205, 150, 3));
        fishs.add(new Fish(525, 150, 3));

        // Level 1
        plants.add(new Plant(600, 115, 1));
        // Level 2
        plants.add(new Plant(250, 115, 2));
        plants.add(new Plant(650, 115, 2));
        plants.add(new Plant(1950, 115, 2));
        // Level 3
        plants.add(new Plant(850, 115, 3));
        plants.add(new Plant(1410, 115, 3));

        // Level 2
        //traps.add(new Traps(720, 170, 2));
        traps.add(new Traps(1400, 170, 2));

        for (int i = 0; i < crabs.size(); i++) {
            crabs.get(i).init(this);
            addEntity(crabs.get(i));
        }

        for (int i = 0; i < fishs.size(); i++) {
            fishs.get(i).init(this);
            addEntity(fishs.get(i));
        }

        for (int i = 0; i < plants.size(); i++) {
            plants.get(i).init(this);
            addEntity(plants.get(i));
        }
    }

    // fonction permettant le rendu d'objet sur le niveau 1
    public void level1Objects() {
        for (int i = 0; i < clouds.size(); i++) {
            if(clouds.get(i).getLevel1() == true) {
                clouds.get(i).render();
            }
        }

        Header.render();

        textureFruit.bind();
        for (int i = 0; i < fruits.size(); i++) {
            if (fruits.get(i).getEat() == false && fruits.get(i).getLevel() == 1)
                Renderer.renderEntity(fruits.get(i).getX(), fruits.get(i).getY(), 37, 37, Colors.WHITE, 0.5f, 0, 0);
        }
        textureFruit.unbind();

        for (int i = 0; i < boxes.size(); i++) {
            if(boxes.get(i).getBreak() != true)
                boxes.get(i).render();
        }

        for (int i = 0; i < checkpoints.size(); i++) {
            checkpoints.get(i).render();
        }

        for (int i = 0; i < fruits.size(); i++) {
            if (fruits.get(i).getEat() == false && fruits.get(i).getLevel() == 1)
                fruits.get(i).setY(fruits.get(i).getDefaultY() + animFruits.getCurrentCoord());
        }
    }

    // fonction permettant le rendu d'objet sur le niveau 2
    public void level2Objects() {
        for (int i = 0; i < clouds.size(); i++) {
            clouds.get(i).render();
        }

        Header.render();

        textureFruit.bind();
        for (int i = 0; i < fruits.size(); i++) {
            if (fruits.get(i).getEat() == false && fruits.get(i).getLevel() == 2)
                Renderer.renderEntity(fruits.get(i).getX(), fruits.get(i).getY(), 37, 37, Colors.WHITE, 0.5f, 0, 0);
        }
        textureFruit.unbind();

        for (int i = 0; i < boxes.size(); i++) {
            if(boxes.get(i).getBreak() != true)
                boxes.get(i).render();
        }

        for (int i = 0; i < checkpoints.size(); i++) {
            checkpoints.get(i).render();
        }

        for (int i = 0; i < traps.size(); i++) {
            traps.get(i).render();
        }

        for (int i = 0; i < fruits.size(); i++) {
            if (fruits.get(i).getEat() == false && fruits.get(i).getLevel() == 2)
                fruits.get(i).setY(fruits.get(i).getDefaultY() + animFruits.getCurrentCoord());
        }
    }

    // fonction permettant le rendu d'objet sur le niveau 3
    public void level3Objects() {
        for (int i = 0; i < clouds.size(); i++) {
            clouds.get(i).render();
        }

        Header.render();

        textureFruit.bind();
        for (int i = 0; i < fruits.size(); i++) {
            if (fruits.get(i).getEat() == false && fruits.get(i).getLevel() == 3)
                Renderer.renderEntity(fruits.get(i).getX(), fruits.get(i).getY(), 37, 37, Colors.WHITE, 0.5f, 0, 0);
        }
        textureFruit.unbind();

        for (int i = 0; i < boxes.size(); i++) {
            if(boxes.get(i).getBreak() != true)
                boxes.get(i).render();
        }

        for (int i = 0; i < checkpoints.size(); i++) {
            checkpoints.get(i).render();
        }

        for (int i = 0; i < fruits.size(); i++) {
            if (fruits.get(i).getEat() == false && fruits.get(i).getLevel() == 3)
                fruits.get(i).setY(fruits.get(i).getDefaultY() + animFruits.getCurrentCoord());
        }
    }

    // les fonctions suivantes sont déjà en place pour de futur MàJ
    // pour le moment elles n'ont pas d'utilités mais laissons les tranquilles,
    // leurs heures viendra !

    public void load1AfterPlayer() {

    }

    public void load2AfterPlayer() {

    }

    public void load3AfterPlayer() {

    }

    // fonction permettant de rmettre à 0 tout ce jolie petit monde (fruits, caisses, ennemies ...)
    public void reloadObject() {
        // Reload fruits
        for (int i = 0; i < fruits.size(); i++) {
            if(fruits.get(i).getEat() == true) fruits.get(i).setEat(false);
        }

        // Reload boxes
        for (int i = 0; i < boxes.size(); i++) {
            if(boxes.get(i).getBreak() == true)
                boxes.get(i).setBreak(false);
        }

        for (int i = 0; i < crabs.size(); i++) {
            if(crabs.get(i).getEnabled() == false) crabs.get(i).setEnabled(true);
        }

        for (int i = 0; i < plants.size(); i++) {
            if(plants.get(i).getEnabled() == false) plants.get(i).setEnabled(true);
        }
    }

    // fonction de remise à 0 après un game over
    public static void reloadGameOver() {
        Level.levelNumber = 1;
        initObjects();

        for (int i = 0; i < checkpoints.size(); i++) {
            if(checkpoints.get(i).getChecked() == true) checkpoints.get(i).setChecked(false);
        }
    }

    // fonction de déchargement du level pour vider la mémoire (plus utilisé depuis la dernière MàJ car jugé trop instable)
    public static void levelDischarge() { /* R.I.P */ }

    // fonction de vérification des collisions de toutes les caisses sur l'axe X
    public static boolean checkCollideAllBoxesX() {
        for (int i = 0; i < boxes.size(); i++) {
            if(!boxes.get(i).getBreak()) {
                if(Level.boxes.get(i).getCollideX()) {
                    if(boxes.get(i).getTornadoBreak() && Player.tornadoAttack) {
                        boxes.get(i).setBreak(true);
                        boxes.get(i).onAction();
                        return false;
                    } else {
                        if(Player.tornadoAttack) boxes.get(i).onAction();
                        return true;
                    }
                }
            }
        }

        return false;
    }

    // fonction de vérification des collisions de toutes les caisses sur l'axe Y
    public static boolean checkCollideAllBoxesY() {
        for (int i = 0; i < boxes.size(); i++) {
            if(!boxes.get(i).getBreak()) {
                if(Level.boxes.get(i).getCollideY()) return true;
            }
        }

        return false;
    }

    static double gain;

    public static int levelSoundIsPlaying = 0;
    // Gestion des sons d'arrière plant des niveaux
    public static void startLevelSound(int lvl) {

        if(lvl == 1) {
            if(Component.soundLevelEnabled == -1) gain = 10;
            else gain = Component.soundLevelEnabled;
            lvl1Sound.init(new File("./res/sounds/lvl1.wav"));
            lvl1Sound.play();
            lvl1Sound.setVolume((float) gain / 100);
            levelSoundIsPlaying = 1;
        } else if(lvl == 2) {
            if(Component.soundLevelEnabled == -1) gain = 40;
            else gain = Component.soundLevelEnabled+20;
            lvl2Sound.init(new File("./res/sounds/lvl2.wav"));
            lvl2Sound.play();
            lvl2Sound.setVolume((float) gain / 100);
            levelSoundIsPlaying = 2;
        } else if(lvl == 3) {
            if(Component.soundLevelEnabled == -1) gain = 40;
            else gain = Component.soundLevelEnabled+20;
            lvl3Sound.init(new File("./res/sounds/lvl3.wav"));
            lvl3Sound.play();
            lvl3Sound.setVolume((float) gain / 100);
            levelSoundIsPlaying = 3;
        }

    }

    // petite instance pour la récupération du joueur dans les levels
    public static Player getPlayer() {
        return player;
    }

}

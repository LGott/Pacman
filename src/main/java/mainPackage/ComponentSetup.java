// package mainPackage;
//
// import java.util.ArrayList;
//
// import objectsPackage.BonusPellet;
// import objectsPackage.Ghost;
// import objectsPackage.Pacman;
// import objectsPackage.Pellet;
// import objectsPackage.Wall;
// import objectsPackage.YellowPellet;
// import javafx.collections.ObservableList;
// import javafx.scene.Node;
// import javafx.scene.control.Label;
// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;
// import javafx.scene.paint.Color;
//
// public class ComponentSetup {
//
// private MazeGui mazeGui;
// private ObservableList<Node> group;
// private WorldLogic world;
// private Pacman pacman1;
// private Pacman pacman2;
// private ArrayList<Pacman> pacmanArray;
// private ArrayList<Ghost> ghosts;
// private ArrayList<Pellet> pellets;
//
// public ComponentSetup(MazeGui mazeGui) {
// this.mazeGui = mazeGui;
// this.group = mazeGui.getGroup();
// this.world = mazeGui.getWorld();
// this.pacman1 = mazeGui.getPacman1();
// this.pacman2 = mazeGui.getPacman2();
// this.pacmanArray = mazeGui.getPacmanArray();
// this.ghosts = mazeGui.getGhosts();
// this.pellets = mazeGui.getPellets();
// }
//
// public void createShapes() {
// createWalls();
// createGhosts();
// createPacmans();
// createPellets();
// createBonusPellets();
// }
//
// private void createWalls() {
// // WALLS
// // top wall
// createWall(0, 84, 100, 1);
// // bottom wall
// createWall(0, 4, 100, 1);
// // right wall
// createWall(99, 37, 1, 74);
// // left wall
// createWall(0, 37, 1, 74);
//
// // west
// createWall(11, 15, 3, 3);
// createWall(24, 15, 3, 3);
// createWall(11, 68, 3, 8);
// createWall(11, 39, 3, 14);
// createWall(24, 64, 3, 12);
// createWall(24, 35, 3, 10);
//
// // north
// createWall(37, 60, 3, 3);
// createWall(63, 60, 3, 3);
// createWall(50, 73, 16, 3);
// createWall(50, 65, 3, 8);
//
// // south
// createWall(50, 28, 16, 3);
// createWall(37, 8, 3, 3);
// createWall(50, 15, 3, 3);
// createWall(63, 8, 3, 3);
//
// // east
// createWall(95, 69, 3, 7);
// createWall(95, 40, 3, 9);
// createWall(82, 15, 9, 3);
// createWall(82, 59, 3, 3);
// createWall(82, 28, 3, 3);
//
// createWall(76, 66, 3, 10);
// createWall(76, 37, 3, 12);
//
// // center
// createWall(50, 48, 9, 1);
// createWall(50, 39, 9, 1);
//
// }
//
// private void createWall(int posX, int posY, int width, int height) {
// group.add(new Wall(posX, posY, mazeGui.getWorld(), width, height,
// Color.BLUE).getNode());
// }
//
// public void createPacmans() {
// pacman1 = createPacman(50, 80, "Pacman1");
// pacman2 = createPacman(50, 22, "Pacman2");
// pacmanArray.add(pacman1);
// pacmanArray.add(pacman2);
// mazeGui.setPacmanArray(pacmanArray);
// }
//
// private Pacman createPacman(int x, int y, String name) {
// Pacman pacman = new Pacman(x, y, world, name);
// group.add(pacman.getNode());
// return pacman;
// }
//
// // Animated Ghosts
// void createGhosts() {
// ghosts.add(new Ghost(42, 44, world, new Image[] {
// new Image(getClass().getResourceAsStream("/pacman-images/g_blue_down1.png")),
// new Image(getClass().getResourceAsStream("/pacman-images/g_blue_down2.png")),
// new Image(getClass().getResourceAsStream("/pacman-images/g_blue_up1.png")),
// new Image(getClass().getResourceAsStream("/pacman-images/g_blue_up2.png")),
// new Image(getClass().getResourceAsStream("/pacman-images/g_blue_left1.png")),
// new Image(getClass().getResourceAsStream("/pacman-images/g_blue_left2.png")),
// new
// Image(getClass().getResourceAsStream("/pacman-images/g_blue_right1.png")),
// new
// Image(getClass().getResourceAsStream("/pacman-images/g_blue_right2.png")),
// new Image(getClass().getResourceAsStream("/pacman-images/invincible1.png")),
// new Image(getClass().getResourceAsStream("/pacman-images/invincible2.png"))
// }));
//
// ghosts.add(new Ghost(47, 44, world, new Image[] {
// new Image(getClass().getResourceAsStream("/pacman-images/g_pink_down1.png")),
// new Image(getClass().getResourceAsStream("/pacman-images/g_pink_down2.png")),
// new Image(getClass().getResourceAsStream("/pacman-images/g_pink_up1.png")),
// new Image(getClass().getResourceAsStream("/pacman-images/g_pink_up2.png")),
// new Image(getClass().getResourceAsStream("/pacman-images/g_pink_left1.png")),
// new Image(getClass().getResourceAsStream("/pacman-images/g_pink_left2.png")),
// new
// Image(getClass().getResourceAsStream("/pacman-images/g_pink_right1.png")),
// new
// Image(getClass().getResourceAsStream("/pacman-images/g_pink_right2.png")),
// new Image(getClass().getResourceAsStream("/pacman-images/invincible1.png")),
// new Image(getClass().getResourceAsStream("/pacman-images/invincible2.png"))
// }));
//
// ghosts.add(new Ghost(53, 44, world, new Image[] {
// new
// Image(getClass().getResourceAsStream("/pacman-images/g_orange_down1.png")),
// new
// Image(getClass().getResourceAsStream("/pacman-images/g_orange_down2.png")),
// new Image(getClass().getResourceAsStream("/pacman-images/g_orange_up1.png")),
// new Image(getClass().getResourceAsStream("/pacman-images/g_orange_up2.png")),
// new
// Image(getClass().getResourceAsStream("/pacman-images/g_orange_left1.png")),
// new
// Image(getClass().getResourceAsStream("/pacman-images/g_orange_left2.png")),
// new
// Image(getClass().getResourceAsStream("/pacman-images/g_orange_right1.png")),
// new
// Image(getClass().getResourceAsStream("/pacman-images/g_orange_right2.png")),
// new Image(getClass().getResourceAsStream("/pacman-images/invincible1.png")),
// new Image(getClass().getResourceAsStream("/pacman-images/invincible2.png"))
// }));
//
// ghosts.add(new Ghost(58, 44, world, new Image[] {
// new Image(getClass().getResourceAsStream("/pacman-images/g_red_down1.png")),
// new Image(getClass().getResourceAsStream("/pacman-images/g_red_down2.png")),
// new Image(getClass().getResourceAsStream("/pacman-images/g_red_up1.png")),
// new Image(getClass().getResourceAsStream("/pacman-images/g_red_up2.png")),
// new Image(getClass().getResourceAsStream("/pacman-images/g_red_left1.png")),
// new Image(getClass().getResourceAsStream("/pacman-images/g_red_left2.png")),
// new Image(getClass().getResourceAsStream("/pacman-images/g_red_right1.png")),
// new Image(getClass().getResourceAsStream("/pacman-images/g_red_right2.png")),
// new Image(getClass().getResourceAsStream("/pacman-images/invincible1.png")),
// new Image(getClass().getResourceAsStream("/pacman-images/invincible2.png"))
// }));
//
// for (Ghost g : ghosts) {
// group.add(g.getNode());
// }
// }
//
// void createPellets() {
// // bottom line across
// for (int i = 5; i < 31; i += 8) {
// createYellowPellet(i, 8);
// }
// createYellowPellet(43, 8);
// createYellowPellet(50, 8);
// createYellowPellet(57, 8);
// for (int i = 70; i < 95; i += 8) {
// createYellowPellet(i, 8);
// }
// // second to bottom across
// createYellowPellet(29, 15);
// createYellowPellet(36, 15);
// createYellowPellet(43, 15);
//
// createYellowPellet(57, 15);
// createYellowPellet(63, 15);
// createYellowPellet(70, 15);
// // third to bottom across
// for (int i = 22; i < 50; i += 7) {
// createYellowPellet(i, 22);
// }
// for (int i = 57; i < 90; i += 7) {
// createYellowPellet(i, 22);
// }
// createYellowPellet(12, 22);
// createYellowPellet(18, 15);
// // vertical left column
// for (int i = 16; i < 75; i += 7) {
// createYellowPellet(5, i);
// }
// // second to left vertical column
// for (int i = 30; i < 75; i += 7) {
// createYellowPellet(18, i);
// }
// // top row across
// for (int i = 5; i < 45; i += 7) {
// createYellowPellet(i, 80);
// }
// for (int i = 59; i < 95; i += 7) {
// createYellowPellet(i, 80);
// }
// // left inner home down
// for (int i = 34; i < 60; i += 7) {
// createYellowPellet(31, i);
// }
// for (int i = 34; i < 60; i += 7) {
// createYellowPellet(38, i);
// }
// createYellowPellet(31, 61);
// // right inner home down
// for (int i = 34; i < 60; i += 7) {
// createYellowPellet(62, i);
// }
// for (int i = 34; i < 60; i += 7) {
// createYellowPellet(69, i);
// }
// createYellowPellet(69, 61);
// // horizontal under home
// for (int i = 44; i < 62; i += 6) {
// createYellowPellet(i, 34);
// }
// // vertical above home
// for (int i = 55; i < 72; i += 6) {
// createYellowPellet(56, i);
// }
// for (int i = 55; i < 72; i += 6) {
// createYellowPellet(44, i);
// }
// // horizontal above home
// for (int i = 31; i < 45; i += 7) {
// createYellowPellet(i, 67);
// }
// for (int i = 62; i < 75; i += 7) {
// createYellowPellet(i, 67);
// }
// // vertical third to right column
// for (int i = 35; i < 58; i += 8) {
// createYellowPellet(82, i);
// }
// for (int i = 66; i < 75; i += 7) {
// createYellowPellet(82, i);
// }
// // right vertical
// createYellowPellet(94, 51);
// createYellowPellet(94, 59);
// for (int i = 14; i < 30; i += 7) {
// createYellowPellet(94, i);
// }
// // second to right vertical
// for (int i = 59; i < 75; i += 7) {
// createYellowPellet(87, i);
// }
// for (int i = 35; i < 58; i += 8) {
// createYellowPellet(87, i);
// }
// // mazeGui.setPellets(pellets);
// }
//
// private void createYellowPellet(int posX, int posY) {
// Pellet p = new YellowPellet(posX, posY, world);
// addPellet(p);
// }
//
// void createBonusPellets() {
// createBonusPellet(31, 74);
// createBonusPellet(70, 74);
// createBonusPellet(11, 57);
// createBonusPellet(50, 54);
// createBonusPellet(24, 49);
// createBonusPellet(88, 28);
// createBonusPellet(17, 22);
// createBonusPellet(70, 28);
// createBonusPellet(31, 28);
// createBonusPellet(76, 53);
// }
//
// private void createBonusPellet(int posX, int posY) {
// Pellet bp = new BonusPellet(posX, posY, world, 10);
// addPellet(bp);
// }
//
// private void addPellet(Pellet p) {
// pellets.add(p);
// group.add(p.getNode());
// }
//
// }

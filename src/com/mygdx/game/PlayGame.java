package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

/**
 * Created by Hairuo on 7/16/2015.
 */
public class PlayGame implements Screen,ApplicationListener,InputProcessor {
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private TextureRegion region;
    private Texture texture;
    private Unit testUnit;
    private Player player = new Player();
    private Rectangle dragBox = new Rectangle();
    private ArrayList<Unit> selected = new ArrayList<Unit>();
    private ArrayList<Unit> allUnits = new ArrayList<Unit>();
    private boolean isDragging = false;
    private int mouseX,mouseY,cMouseX,cMouseY = 0;
    private ShapeRenderer sRenderer = new ShapeRenderer();


    @Override
    public void show() {

        TmxMapLoader loader = new TmxMapLoader();
        map = new TiledMap();
        camera = new OrthographicCamera();
        texture = new Texture("Maps/TestTile1.png");
        region = new TextureRegion(texture, 0, 0, 16, 16);
        int[][] collisionBlocks = new int[64][48];


        MapLayers layers = map.getLayers();
        TiledMapTileLayer ImageLayer = new TiledMapTileLayer(64, 48, 16, 16);
        ImageLayer.setName("imageLayer");
        TiledMapTileLayer.Cell cellTest = new TiledMapTileLayer.Cell();
        TiledMapTile testTile = new StaticTiledMapTile(region);
        cellTest.setTile(testTile);

        for (int x = 0; x < 64; x++) {

            for (int y = 0; y < 48; y++) {
                if(y<10) {
                    ImageLayer.setCell(x, y, cellTest); // new StaticTiledMapTile(region) Create a Tile which lives outside of a TileSet
                    //ImageLayer.getCell(x, y).getTile().setId(1);
                    collisionBlocks[x][y] = 1;
                    // System.out.println(ImageLayer.getCell(x,y).getTile().getId();

                }
            }
        }
        ImageLayer.setCell(32,10,cellTest);
        collisionBlocks[32][10] = 1;
        ImageLayer.setCell(10,10,cellTest);
        collisionBlocks[10][10] = 1;
        layers.add(ImageLayer);
        testUnit = new Unit(new Sprite(new Texture("Maps/TestTile1.png")), collisionBlocks);
        renderer = new OrthogonalTiledMapRenderer(map);
        Gdx.input.setInputProcessor(testUnit);

        allUnits.add(testUnit);




    }

    @Override
    public void render(float delta) {



        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(camera);
        renderer.render();

        renderer.getBatch().begin();
        for(Unit unit : allUnits){
            unit.draw(renderer.getBatch());
        }

        renderer.getBatch().end();

        sRenderer.setProjectionMatrix(camera.combined);

        sRenderer.begin(ShapeRenderer.ShapeType.Line);
        if(isDragging){
            select(allUnits);
            dragBox = new Rectangle(cMouseX,cMouseY,-(cMouseX-mouseX),(cMouseY+mouseY));
            sRenderer.rect(cMouseX,cMouseY,-(cMouseX-mouseX),(cMouseY-mouseY));
        }
        for(Unit unit : allUnits){
            sRenderer.rect(unit.getX(),unit.getY(),16,16);
        }
        sRenderer.end();

        sRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for(Unit unit: selected){
            sRenderer.rect(unit.getX(),unit.getY(),16,16);
        }
        //sRenderer.rect(testUnit.getX(),testUnit.getY(),16,16);
        sRenderer.end();

    }


    @Override
    public void resize(int width, int height) {
        camera.viewportHeight = height;
        camera.viewportWidth = width;
        camera.position.set(width / 2f, height / 2f, 0);
        //camera.translate(200,200);
        camera.update();
    }

    public void select(ArrayList<Unit> units){
        for(Unit unit: units ){
            if(dragBox.overlaps(unit.getHitbox())){
                System.out.println("lol");
                selected.add(unit);
            }
        }

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        cMouseX = screenX;
        cMouseY = screenY;
        if(!isDragging){
            isDragging = true;
        }else{
            isDragging = false;
            selected.clear();
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        System.out.println(screenX+","+screenY);

        mouseX = screenX;
        mouseY = screenY;
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        testUnit.getTexture().dispose();
    }

    @Override
    public void create() {

    }

    @Override
    public void render() {

    }

    public TiledMap getMap() {
        return map;
    }

    public OrthogonalTiledMapRenderer getRenderer() {
        return renderer;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public TextureRegion getRegion() {
        return region;
    }

    public Texture getTexture() {
        return texture;
    }

    public Unit getTestUnit() {
        return testUnit;
    }
}

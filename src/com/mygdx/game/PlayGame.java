package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

/**
 * Created by Hairuo on 7/16/2015.
 */
public class PlayGame implements Screen {
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private TextureRegion region;
    private Texture texture;
    private Unit testUnit;


    @Override
    public void show() {
        TmxMapLoader loader = new TmxMapLoader();
        map = new TiledMap();
        //map = loader.load("Maps/TestMap.tmx");
        //renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        texture = new Texture("Maps/TestTile1.png");
        region = new TextureRegion(texture, 0, 0, 16, 16);
        int[][] collisionBlocks = new int[64][48];

        MapLayers layers = map.getLayers();
        TiledMapTileLayer ImageLayer = new TiledMapTileLayer(64, 48, 16, 16);
        //TiledMapTileLayer collisionLayer = new TiledMapTileLayer(64, 48, 16, 16);
        ImageLayer.setName("imageLayer");
        //collisionLayer.setName("imageLayer");
        TiledMapTileLayer.Cell cellTest = new TiledMapTileLayer.Cell();
        TiledMapTile testTile = new StaticTiledMapTile(region);
        //testTile.setId(1);
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

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(camera);
        renderer.render();

        renderer.getBatch().begin();
        testUnit.draw(renderer.getBatch());
        renderer.getBatch().end();


    }


    @Override
    public void resize(int width, int height) {
        camera.viewportHeight = height;
        camera.viewportWidth = width;
        camera.position.set(width/2f, height/2f, 0);
        //camera.translate(200,200);
        camera.update();
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

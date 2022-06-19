package me.partlysunny.shapewars.screens;

import de.eskalon.commons.screen.transition.impl.BlendingTransition;
import me.partlysunny.shapewars.ShapeWars;

public class Transitions {

    public static void init(ShapeWars shapeWars) {
        shapeWars.getScreenManager().addScreenTransition("blending", new BlendingTransition(shapeWars.batch(), 1));
    }

}

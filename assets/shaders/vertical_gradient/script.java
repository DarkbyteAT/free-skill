import com.badlogic.gdx.graphics.Color;

Color startColour = (Color) parameters.get("u_startColour");
Color endColour = (Color) parameters.get("u_endColour");
Float intensity = (Float) parameters.get("u_intensity");

shaderProgram.setUniformf("u_startColour", startColour.r, startColour.g, startColour.b, startColour.a);
shaderProgram.setUniformf("u_endColour", endColour.r, endColour.g, endColour.b, endColour.a);
shaderProgram.setUniformf("u_intensity", intensity);
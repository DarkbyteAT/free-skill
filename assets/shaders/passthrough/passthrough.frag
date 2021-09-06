uniform sampler2D u_texture;
uniform vec4 u_startColour;
uniform vec4 u_endColour;
uniform float u_intensity;

varying vec2 v_texCoord0;
varying vec4 v_color;
varying vec2 v_gradientVector;

void main() {
    gl_FragColor = texture2D(u_texture, v_texCoord0) * v_color;
}

uniform sampler2D u_texture;

varying vec2 v_texCoord0;
varying vec4 v_color;

void main() {
    gl_FragColor = texture2D(u_texture, v_texCoord0) * v_color;
}

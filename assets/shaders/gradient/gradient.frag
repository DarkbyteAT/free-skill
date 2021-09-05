#version 120

attribute vec2 a_texCoord0;
attribute vec3 a_position;
attribute vec4 a_color;

uniform mat4 u_projTrans;

void main() {
    gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);
}

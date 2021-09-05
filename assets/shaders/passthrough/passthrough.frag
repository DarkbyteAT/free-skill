#version 120

uniform sampler2D u_sampler2D;

varying vec2 v_texCoord0;
varying vec4 v_color;

void main() {
    gl_FragColor = texture2D(u_sampler2D, v_texCoord0) * v_color;
}

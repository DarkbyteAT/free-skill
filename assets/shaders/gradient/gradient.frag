#version 120

uniform vec4 u_startColour;
uniform vec4 u_endColour;
uniform float u_gradientBearing;
uniform sampler2D u_sampler2D;

varying vec2 v_texCoord0;
varying vec4 v_color;

void main() {


    gl_FragColor = texture2D(u_sampler2D, v_texCoord0) * v_color;
}

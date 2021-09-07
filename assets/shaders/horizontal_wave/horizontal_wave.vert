#define PI 3.1415926538

attribute vec2 a_texCoord0;
attribute vec3 a_position;
attribute vec4 a_color;

uniform mat4 u_projTrans;
uniform float u_intensity;
uniform float u_frequency;
uniform float u_time;

varying vec2 v_texCoord0;
varying vec4 v_color;

void main() {
    v_texCoord0 = a_texCoord0;
    v_color = a_color;
    vec4 originalPosition = u_projTrans * vec4(a_position, 1.0);
    gl_Position = vec4(originalPosition.x + (u_intensity * sin((PI * u_frequency * u_time) + originalPosition.x)), originalPosition.y, originalPosition.z, originalPosition.w);
}

#define PI 3.1415926538
#define BLUR_LEVEL 3

attribute vec2 a_texCoord0;
attribute vec3 a_position;
attribute vec4 a_color;

uniform mat4 u_projTrans;

varying vec2 v_texCoord0;
varying vec4 v_color;
varying float v_kernel[BLUR_LEVEL];

float gauss(float x) {
    return inversesqrt(2 * PI * BLUR_LEVEL * BLUR_LEVEL) * exp(-((x * x) / (2 * BLUR_LEVEL * BLUR_LEVEL)));
}

void main() {
    v_texCoord0 = a_texCoord0;
    v_color = a_color;

    for(int i = 0; i < BLUR_LEVEL; i++) {
        v_kernel[i] = gauss(i);
    }

    gl_Position = u_projTrans * vec4(a_position, 1.0);
}
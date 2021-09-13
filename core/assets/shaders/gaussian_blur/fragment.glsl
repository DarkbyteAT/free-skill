#define BLUR_LEVEL 3

uniform sampler2D u_texture;
uniform vec2 u_textureSize;

varying vec2 v_texCoord0;
varying vec4 v_color;
varying float v_kernel[BLUR_LEVEL];

vec4 blur(sampler2D texture, vec2 uv, vec2 direction) {
    vec4 color = vec4(0.0);
    vec2 offset = vec2(1.3333333333333333) * direction;
    color += texture2D(texture, uv) * v_kernel[0];
    color += texelFetch(texture, ivec2(uv) + ivec2(offset), 0) * v_kernel[1];
    color += texelFetch(texture, ivec2(uv) - ivec2(offset), 0) * v_kernel[1];
    color += texelFetch(texture, ivec2(uv) + (2 * ivec2(offset)), 0) * v_kernel[2];
    color += texelFetch(texture, ivec2(uv) - (2 * ivec2(offset)), 0) * v_kernel[2];
    return color;
}

void main() {
    vec4 horizontalBlur = blur(u_texture, v_texCoord0, vec2(BLUR_LEVEL, 0.0));
    vec4 verticalBlur = blur(u_texture, v_texCoord0, vec2(0.0, BLUR_LEVEL));
    gl_FragColor = normalize(horizontalBlur);
}

uniform sampler2D u_texture;
uniform vec4 u_startColour;
uniform vec4 u_endColour;
uniform float u_intensity;

varying vec2 v_texCoord0;
varying vec4 v_color;

void main() {
    vec4 textureColour = texture2D(u_texture, v_texCoord0) * v_color;
    vec3 gradientColour = mix(u_startColour, u_endColour, v_texCoord0.x).rgb;
    gl_FragColor = vec4(mix(textureColour.rgb, gradientColour, u_intensity), textureColour.a);
}

#version 120

attribute vec2 vPosition;

void main() {
    gl_Position = vec4(vPosition, 0, 0);
}

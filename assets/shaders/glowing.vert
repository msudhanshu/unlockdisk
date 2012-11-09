 uniform mat4 uMVPMatrix;
 attribute vec4 v_normal;
 attribute vec4 v_position;
 attribute vec2 vTexCoord;
 
 varying vec2 v_texCoord;
 varying vec3 light;
 
  void main() {
          light = vec3(v_normal.x,v_normal.y,v_normal.z);
          gl_Position = v_position * uMVPMatrix;
          }
 uniform mat4 uMVPMatrix;
  uniform mat4 uMVMatrix;
 uniform mat4 uNormalMatrix; 
 attribute vec4 v_position;
 attribute vec4 v_normal;
 attribute vec2 vTexCoord;
 
 	varying vec3 fragNormal;
	varying vec3 fragEye;
	 varying vec3 light;
  varying float v_Dot;
  
 void main() {
           vec3 lightDir = vec3(1.0,1.0,1.0);
           fragNormal = (uMVMatrix*v_normal).xyz;
	       fragEye = (uMVMatrix*v_position).xyz;
	       fragEye = normalize(fragEye);
	       vec4 transNormal = uNormalMatrix * vec4(v_normal.xyz, 1);
           v_Dot = max(dot(transNormal.xyz, lightDir), 0.0);
        
          light = lightDir;
          gl_Position = v_position * uMVPMatrix;
          }
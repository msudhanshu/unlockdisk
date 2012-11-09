precision mediump float;
uniform vec4 vColor;
 varying vec3 light;
void main() {
         /*    gl_FragColor = vec4(min(1.0,vColor.x*2.0),min(1.0,vColor.y*1.1),min(1.0,vColor.z*1.1),0.2);
             gl_FragColor = vec4(vColor.x,vColor.y,vColor.z,1.0);*/
             gl_FragColor = vec4(light,1.0);
            }
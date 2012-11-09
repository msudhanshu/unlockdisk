precision mediump float;
uniform vec4 vColor;
 	varying vec3 fragNormal;
	varying vec3 fragEye;
	 varying vec3 light;
    varying float v_Dot;
    
void main() {
    float diffuseIntensity;
	float specularItensity;
	vec3 normal = normalize(fragNormal);
	vec3 eye = normalize(fragEye);

	diffuseIntensity = clamp(max(dot(normal,light), 0.0), 0.4, 0.8);
	vec3 reflection;
	reflection = normalize(reflect(light, normal));
	specularItensity = pow(clamp(max(dot(reflection, eye), 0.0), 0.0, 0.1), 20.0 );

        vec4 lightDifcolor = vColor;
     gl_FragColor =  vec4(lightDifcolor.xyz * diffuseIntensity, lightDifcolor.a) + lightDifcolor*specularItensity;

            }
package Models.DataAndLoader;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.media.opengl.GL2;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class ObjectLoader {

    private String objName;
    private String mtllib;
    private ArrayList<float[]> vData = new ArrayList<>(); //list of vertex coordinates
    private ArrayList<float[]> vtData = new ArrayList<>(); //list of texture coordinates
    private ArrayList<float[]> vnData = new ArrayList<>(); //list of normal coordinates
    private ArrayList<int[]> fv = new ArrayList<>(); //face vertex indices
    private ArrayList<int[]> ft = new ArrayList<>(); //face texture indices
    private ArrayList<int[]> fn = new ArrayList<>(); //face normal indices
    private int FaceFormat; //format of the faces triangles or quads
    private HashMap<String,ObjData> objectsData = new HashMap<>();//
    private HashMap<String,ArrayList<String>> mtlToObj = new HashMap<>();
    private HashMap<String,Texture> mtlToTex = new HashMap<>();
    private Material material;

    public ObjectLoader(){

    }

    public ArrayList<ObjData> LoadModelToGL(String inModelPath,GL2 gl){
        //OBJModelPath = inModelPath;
        LoadOBJModel(inModelPath,gl);
        if(mtllib != null){
            LoadMTLModel(mtllib,gl);
        }
        ArrayList<ObjData> objectList = getObjects();
        cleanAll();
        return objectList;
    }
    public ArrayList<ObjData> getObjects(){
        ArrayList<ObjData> objects = new ArrayList<ObjData>();
        objects.addAll(objectsData.values());
        return objects;
    }

    private void LoadOBJModel(String ModelPath,GL2 gl) {
        try {
            BufferedReader br = null;
            if (ModelPath.endsWith(".zip")) {
                ZipInputStream tZipInputStream = new ZipInputStream(new BufferedInputStream((new Object()).getClass().getResourceAsStream(ModelPath)));
                ZipEntry tZipEntry;
                tZipEntry = tZipInputStream.getNextEntry();
                String inZipEntryName = tZipEntry.getName();
                if (!tZipEntry.isDirectory()) {
                    br = new BufferedReader(new InputStreamReader(tZipInputStream));
                }
            } else {
                Object obj = new Object();
                InputStream	myis = ClassLoader.getSystemClassLoader().getResourceAsStream(ModelPath);

                InputStreamReader isr = new InputStreamReader(myis);
                br = new BufferedReader(isr);
            }
            String line = null;
            int firstFlag = 0;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) { //read any descriptor data in the file
                    // Zzzz ...
                } else if (line.equals("")) {
                    // Ignore whitespace data
                } else if (line.startsWith("v ")) { //read in vertex data
                    vData.add(ProcessData(line));
                } else if (line.startsWith("vt ")) { //read texture coordinates
                    vtData.add(ProcessData(line));
                } else if (line.startsWith("vn ")) { //read normal coordinates
                    vnData.add(ProcessData(line));
                } else if (line.startsWith("f ")) { //read face data
                    ProcessfData(line);
                } else if (line.startsWith("o ")) { // new object
                    if(firstFlag == 0){
                        firstFlag =1;
                    } else {
                        AddToObjects(gl);
                    }
                    objName = ProcessSData(line);
                } else if (line.startsWith("usemtl ")){
                    String mtlName = ProcessSData(line);
                    if(mtlToObj.containsKey(mtlName)){
                        mtlToObj.get(mtlName).add(objName);
                    } else {
                        ArrayList<String> objList = new ArrayList<>();
                        objList.add(objName);
                        mtlToObj.put(ProcessSData(line),objList);
                    }

                } else if (line.startsWith("mtllib ")){
                    int index = ModelPath.lastIndexOf('/');
                    mtllib = ModelPath.substring(0,index) + "/" + ProcessSData(line);
                }

            }
            br.close();
            AddToObjects(gl);
            vData.clear();
            vtData.clear();
            vnData.clear();
        } catch (IOException e) {
        }
    }

    private void LoadMTLModel(String ModelPath,GL2 gl) {
        try {
            BufferedReader br = null;
            if (ModelPath.endsWith(".zip")) {
                ZipInputStream tZipInputStream = new ZipInputStream(new BufferedInputStream((new Object()).getClass().getResourceAsStream(ModelPath)));
                ZipEntry tZipEntry;
                tZipEntry = tZipInputStream.getNextEntry();
                String inZipEntryName = tZipEntry.getName();
                if (!tZipEntry.isDirectory()) {
                    br = new BufferedReader(new InputStreamReader(tZipInputStream));
                }
            } else {
                Object obj = new Object();
                InputStream	myis = ClassLoader.getSystemClassLoader().getResourceAsStream(ModelPath);
                if(myis == null){
                    return;
                }
                InputStreamReader isr = new InputStreamReader(myis);
                br = new BufferedReader(isr);
            }
            String line = null;
            String mtl = null;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) { //read any descriptor data in the file
                    // Zzzz ...
                } else if (line.equals("")) {
                    // Ignore whitespace data
                } else if (line.startsWith("newmtl ")) { //read in vertex data
                    if(material != null)
                        AddMaterial(gl,mtl);
                    material = new Material();
                    mtl = ProcessSData(line);
                } else if (line.startsWith("Ns ")) { //read texture coordinates
                    material.setNs(ProcessData(line)[0]);
                } else if (line.startsWith("Ka ")) { //read normal coordinates
                    material.setKa(ProcessData(line));
                } else if (line.startsWith("Kd ")) { //read face data
                    material.setKd(ProcessData(line));
                } else if (line.startsWith("Ks ")) { // new object
                    material.setKs(ProcessData(line));
                } else if (line.startsWith("Ni ")){
                    material.setNi(ProcessData(line)[0]);
                }else if (line.startsWith("d ")){
                    material.setD(ProcessData(line)[0]);
                }else if (line.startsWith("illum ")){
                    material.setIllum(ProcessData(line)[0]);
                }else if (line.startsWith("map_Kd ")){
                    Texture texture = getTextureFromPath(ModelPath, line);
                    if(texture!=null)
                        mtlToTex.put(mtl,texture);
                }
            }
            AddMaterial(gl,mtl);
            //objectsData.get(obj).setMaterial(material);
            br.close();
        } catch (IOException e) {
        }
    }

    private Texture getTextureFromPath(String ModelPath, String line) {
        try {
            String texPath = ProcessSData(line);
            int index = ModelPath.lastIndexOf('/');
            String tex = ModelPath.substring(0, index) + "/" + texPath;
            InputStream myis = ClassLoader.getSystemClassLoader().getResourceAsStream(tex);
            String data[] = texPath.split("\\.");
            return TextureIO.newTexture(myis, true, data[1]);
        } catch(Exception e){

        }
        return null;
    }

    private void AddToObjects(GL2 gl){
        SetFaceRenderType();
        ObjData object = new ObjData();
        //create the object data in gl and get the listID
        object.setList(ConstructInterleavedArray(gl));
        objectsData.put(objName,object);
        cleanForNextObj();
    }
    private void AddMaterial(GL2 gl,String mtl){
        ArrayList<String> objects = mtlToObj.get(mtl);
        for (String obj : objects) {
            objectsData.get(obj).setTexture(mtlToTex.get(mtl));
            objectsData.get(obj).setMaterial(material);
        }
    }
    private String ProcessSData(String read) {
        final String obj[] = read.split("\\s+");
        return obj[1];
    }
    private float[] ProcessData(String read) {
        final String s[] = read.split("\\s+");
        return (ProcessFloatData(s)); //returns an array of processed float data
    }
    private float[] ProcessFloatData(String sdata[]) {
        float data[] = new float[sdata.length - 1];
        for (int loop = 0; loop < data.length; loop++) {
            data[loop] = Float.parseFloat(sdata[loop + 1]);
        }
        return data; //return an array of floats
    }
    private void ProcessfData(String fread) {
        String s[] = fread.split("\\s+");
        if (fread.contains("//")) { //pattern is present if obj has only v and vn in face data
            for (int loop = 1; loop < s.length; loop++) {
                s[loop] = s[loop].replaceAll("//", "/0/"); //insert a zero for missing vt data
            }
        }
        ProcessfIntData(s); //pass in face data
    }
    private void ProcessfIntData(String sdata[]) {
        int vdata[] = new int[sdata.length - 1];
        int vtdata[] = new int[sdata.length - 1];
        int vndata[] = new int[sdata.length - 1];
        for (int loop = 1; loop < sdata.length; loop++) {
            String s = sdata[loop];
            String[] temp = s.split("/");
            vdata[loop - 1] = Integer.valueOf(temp[0]); //always add vertex indices
            if (temp.length > 1) { //we have v and vt data
                vtdata[loop - 1] = Integer.valueOf(temp[1]); //add in vt indices
            } else {
                vtdata[loop - 1] = 0; //if no vt data is present fill in zeros
            }
            if (temp.length > 2) { //we have v, vt, and vn data
                vndata[loop - 1] = Integer.valueOf(temp[2]); //add in vn indices
            } else {
                vndata[loop - 1] = 0; //if no vn data is present fill in zeros
            }
        }
        fv.add(vdata);
        ft.add(vtdata);
        fn.add(vndata);
    }

    private void SetFaceRenderType() {
        final int temp[] = (int[]) fv.get(0);
        if (temp.length == 3) {
            FaceFormat = GL2.GL_TRIANGLES; //the faces come in sets of 3 so we have triangular faces
        } else if (temp.length == 4) {
            FaceFormat = GL2.GL_QUADS; //the faces come in sets of 4 so we have quadrilateral faces
        } else {
            FaceFormat = GL2.GL_POLYGON; //fall back to render as free form polygons
        }
    }

    private int ConstructInterleavedArray(GL2 gl) {
        int list = 0;
        final int tv[] = (int[]) fv.get(0);
        final int tt[] = (int[]) ft.get(0);
        final int tn[] = (int[]) fn.get(0);
        //if a value of zero is found that it tells us we don't have that type of data
        if ((tv[0] != 0) && (tt[0] != 0) && (tn[0] != 0)) {
            list = ConstructTNV(gl); //we have vertex, 2D texture, and normal Data
        } else if ((tv[0] != 0) && (tt[0] != 0) && (tn[0] == 0)) {
            list = ConstructTV(gl); //we have just vertex and 2D texture Data
        } else if ((tv[0] != 0) && (tt[0] == 0) && (tn[0] != 0)) {
            list = ConstructNV(gl); //we have just vertex and normal Data
        } else if ((tv[0] != 0) && (tt[0] == 0) && (tn[0] == 0)) {
            list = ConstructV(gl);
        }
        return list;
    }

    private int ConstructTNV(GL2 gl) {
        int[] v, t, n;
        float tcoords[] = new float[2];
        float coords[] = new float[3];
        //create the gl list and start draw
        int list;
        list = gl.glGenLists(1);
        gl.glNewList(list,GL2.GL_COMPILE);
        gl.glBegin(FaceFormat);
        for (int oloop = 0; oloop < fv.size(); oloop++) {
            v = (int[]) (fv.get(oloop));
            t = (int[]) (ft.get(oloop));
            n = (int[]) (fn.get(oloop));
            for (int iloop = 0; iloop < v.length; iloop++) {
                //fill in the texture coordinate data
                for (int tloop = 0; tloop < tcoords.length; tloop++)
                    tcoords[tloop] = ((float[]) vtData.get(t[iloop] - 1))[tloop];
                gl.glTexCoord2f(tcoords[0],tcoords[1]);

                //fill in the normal coordinate data
                for (int vnloop = 0; vnloop < coords.length; vnloop++)
                    coords[vnloop] = ((float[]) vnData.get(n[iloop] - 1))[vnloop];
                gl.glNormal3f(coords[0],coords[1],coords[2]);

                //fill in the vertex coordinate data
                for (int vloop = 0; vloop < coords.length; vloop++)
                    coords[vloop] = ((float[]) vData.get(v[iloop] - 1))[vloop];
                gl.glVertex3f(coords[0],coords[1],coords[2]);
            }
        }
        gl.glEnd();
        gl.glEndList();
        return list;
    }

    private int ConstructTV(GL2 gl) {
        int[] v, t;
        float tcoords[] = new float[2];
        float coords[] = new float[3];
        //create the gl list and start draw
        int list;
        list = gl.glGenLists(1);
        gl.glNewList(list,GL2.GL_COMPILE);
        gl.glBegin(FaceFormat);
        for (int oloop = 0; oloop < fv.size(); oloop++) {
            v = (int[]) (fv.get(oloop));// get the coord
            t = (int[]) (ft.get(oloop));// get the tcoord
            for (int iloop = 0; iloop < v.length; iloop++) {
                //fill in the texture coordinate data
                for (int tloop = 0; tloop < tcoords.length; tloop++)
                    tcoords[tloop] = ((float[]) vtData.get(t[iloop] - 1))[tloop];
                gl.glTexCoord2f(tcoords[0],tcoords[1]);

                //fill in the vertex coordinate data
                for (int vloop = 0; vloop < coords.length; vloop++)
                    coords[vloop] = ((float[]) vData.get(v[iloop] - 1))[vloop];
                gl.glVertex3f(coords[0],coords[1],coords[2]);
            }
        }
        gl.glEnd();
        gl.glEndList();
        return list;
    }

    private int ConstructNV(GL2 gl) {
        int[] v, n;
        float coords[] = new float[3];
        //create the gl list and start draw
        int list;
        list = gl.glGenLists(1);
        gl.glNewList(list,GL2.GL_COMPILE);
        gl.glBegin(FaceFormat);
        for (int oloop = 0; oloop < fv.size(); oloop++) {
            v = (int[]) (fv.get(oloop));
            n = (int[]) (fn.get(oloop));
            for (int iloop = 0; iloop < v.length; iloop++) {
                //fill in the normal coordinate data
                for (int vnloop = 0; vnloop < coords.length; vnloop++)
                    coords[vnloop] = ((float[]) vnData.get(n[iloop] - 1))[vnloop];
                gl.glNormal3f(coords[0],coords[1],coords[2]);

                //fill in the vertex coordinate data
                for (int vloop = 0; vloop < coords.length; vloop++)
                    coords[vloop] = ((float[]) vData.get(v[iloop] - 1))[vloop];
                gl.glVertex3f(coords[0],coords[1],coords[2]);
            }
        }
        gl.glEnd();
        gl.glEndList();
        return list;
    }

    private int ConstructV(GL2 gl) {

        int[] v;
        float coords[] = new float[3];
        //create the gl list and start draw
        int list;
        list = gl.glGenLists(1);
        gl.glNewList(list,GL2.GL_COMPILE);
        gl.glBegin(FaceFormat);
        for (int oloop = 0; oloop < fv.size(); oloop++) {
            v = (int[]) (fv.get(oloop));
            for (int iloop = 0; iloop < v.length; iloop++) {
                //fill in the vertex coordinate data
                for (int vloop = 0; vloop < coords.length; vloop++)
                    coords[vloop] = ((float[]) vData.get(v[iloop] - 1))[vloop];
                gl.glVertex3f(coords[0],coords[1],coords[2]);
            }
        }
        gl.glEnd();
        gl.glEndList();
        return list;
    }

    private void cleanForNextObj() {
        fv.clear();
        ft.clear();
        fn.clear();
    }
    private void cleanAll(){
        cleanForNextObj();
        vData.clear();
        vtData.clear();
        vnData.clear();
        objectsData.clear();
        mtlToObj.clear();
        mtlToTex.clear();
        mtllib = null;
        material = null;
    }

}
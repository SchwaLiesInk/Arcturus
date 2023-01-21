/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

import static arcengine.Maths.lerp;
import java.awt.Graphics2D;

/**
 *
 * @author Gerwyn Jones
 */
class LandScapeSection implements OneStateFunction {

    double h;
    double flatness;
    double depth;
    float noi;
    int lod = 1;
    int lodStep = 1;
//RingList<Particle>particles=new RingList();
    //NumberSequance ns[][];

    //IntegralOperation particles[][];
    double particleSum[][];

    @Override
    public double f(double x) {
        return (1.0 / x - x * (1.0 - 1.0 / Particle.b));
    }

    int LodStep(int lods) {

        if (lods > 10) {
            lods = 10;
        }
        int step = (LandScape.LandSize >> lods);
        if (step < 1) {
            step = 1;
        }
        if (step > 128) {
            step = 128;
        }
        return step;
    }

    RingList<Tessa> GetScape(int lods, float scalexz, float scaley, float dlim, Molecula mc, Pigment stain) {
        if (lods > 10) {
            lods = 10;
        }
        this.lod = lods;
        lodStep = (LandScape.LandSize >> lod);
        if (lodStep < 1) {
            lodStep = 1;
        }
        if (lodStep > LandScape.LandMiddle >> 1) {
            lodStep = LandScape.LandMiddle >> 1;
        }
        double hs = h * scaley;

        scalexz *= (1 + (float) (lodStep) / (LandScape.LandMiddle / (LandScape.quad * 0.1)));
        RingList<Tessa> tess = new RingList();
        double mult = 1.0 / LandScape.LandMiddle;
        double actual = mult * lodStep;
        double mx = actual + actual;
        Normal n = new Normal(0, 1, 0);
        for (int i = lodStep + lodStep; i < LandScape.LandSize; i += lodStep) {
            double my = actual + actual;

            double sx = scalexz;
            double ax = i - LandScape.LandMiddle;
            for (int j = lodStep + lodStep; j < LandScape.LandSize; j += lodStep) {
                double ay = j - LandScape.LandMiddle;
                //double mr=Math.sqrt(mx*mx+my*my);
                double s = particleSum[i][j] + particleSum[i][j] * mc.NextDouble();
                if (s < h) {
                    s = h;
                }
                double r1 = Math.sqrt((ax - lodStep) * (ax - lodStep) + ay * ay);
                double r2 = Math.sqrt((ax - lodStep) * (ax - lodStep) + (ay - lodStep) * (ay - lodStep));
                double r3 = Math.sqrt(ax * ax + (ay - lodStep) * (ay - lodStep));
                double r4 = Math.sqrt(ax * ax + ay * ay);
                //double r3=LandScape.LandSize*scaley/r2;
                double h1 = hs * LandScape.LandMiddle / (1.0 + r1);
                double h2 = hs * LandScape.LandMiddle / (1.0 + r2);
                double h3 = hs * LandScape.LandMiddle / (1.0 + r3);
                double h4 = hs * LandScape.LandMiddle / (1.0 + r4);
                double sr = (s * stain.Red()*0.004) * mc.NextDouble();
                double sg = (128 + s * stain.Green()*0.004) * mc.NextDouble();
                double sb = (Math.abs(128 + s * stain.Blue()*0.004)) * mc.NextDouble();
                if (s == 0) {
                    sr = 0;
                    sg = 0;

                } else if (s < h * 32) {
                    sb = 0;
                }
                double sz = scalexz;
                //stroke(sr,sg,sb,255);
                float s1 = (float) (particleSum[i - lodStep][j] * scaley - hs);
                float s2 = (float) (particleSum[i - lodStep][j - lodStep] * scaley - hs);
                float s3 = (float) (particleSum[i][j - lodStep] * scaley - hs);
                float s4 = (float) (particleSum[i][j] * scaley - hs);
                if (s1 > h1) {
                    s1 = 0;
                }
                if (s2 > h2) {
                    s2 = 0;
                }
                if (s3 > h3) {
                    s3 = 0;
                }
                if (s4 > h4) {
                    s4 = 0;
                }
                //
                if (s1 < dlim) {
                    s1 = dlim;
                }
                if (s2 < dlim) {
                    s2 = dlim;
                }
                if (s3 < dlim) {
                    s3 = dlim;
                }
                if (s4 < dlim) {
                    s4 = dlim;
                }
                Vector v1 = new Vector((ax - lodStep) * sx, s1, (ay) * sz);
                Vector v2 = new Vector((ax - lodStep) * sx, s2, (ay - lodStep) * sz);
                Vector v3 = new Vector((ax) * sx, s3, (ay - lodStep) * sz);
                Vector v4 = new Vector((ax) * sx, s4, (ay) * sz);
                Vector c = new Vector(sr * 0.039, sg * 0.039, sb * 0.039, 1);
                Vector n1 = new Vector(sx * 4, s1 + s2 + s3 + s4, sz * 4).Unity().AsVector();
                Normal n2 = n1.AsNormal();
                n2 = n2.Add(n2, n).Scaled(0.5).AsNormal();
                TessaVertex tv1 = new TessaVertex(v1, n2, c, -s1);
                TessaVertex tv2 = new TessaVertex(v2, n2, c, -s2);
                TessaVertex tv3 = new TessaVertex(v3, n2, c, -s3);
                TessaVertex tv4 = new TessaVertex(v4, n2, c, -s4);
                Tessa t1 = new Tessa(tv1, tv2, tv3, lodStep);
                Tessa t2 = new Tessa(tv3, tv4, tv1, lodStep);
                tess.Append(t1);
                tess.Append(t2);

                if (ay < 0) {
                    my += actual;
                } else if (ay > 0) {
                    my -= actual;

                }
            }
            if (ax < 0) {
                mx += actual;
            } else if (ax > 0) {
                mx -= actual;

            }
        }
        return tess;
    }

    LandScapeSection(int lod, double globaldepth, double globalflatness, double globalh) {
        if (lod > 10) {
            lod = 10;
        }
        this.lod = lod;
        lodStep = (LandScape.LandSize >> lod);
        if (lodStep < 1) {
            lodStep = 1;
        }
        if (lodStep > LandScape.LandMiddle >> 1) {
            lodStep = LandScape.LandMiddle >> 1;
        }
//

        h = 5.0 + Random.Next(3) - Random.Next(3);
        flatness = 1.0 + Random.Next(1.0f);
        depth = 1.0 + Random.Next(2.0f) - Random.Next(2.0f);
        noi = (0.5f + Random.Next((float) Math.PI));

        //particles = new IntegralOperation[512][512];
        //ns = new NumberSequance[512][512];
        particleSum = new double[LandScape.LandSize][LandScape.LandSize];
        /*for (int i = 0; i < 512; i++) {
         double ix = i + (ox * 512.0);
         double ci = (double) (ix - 256) / 512;
         for (int j = 0; j < 512; j++) {
         double jy = j + (oy * 512.0);
         double cj = (double) (jy - 256) / 512;
         double r2 = (double) (ci * ci + cj * cj);//-n/512.0
         double ax = (double) Math.abs(ix - jy) / (jy * 512);
         double ay = (double) Math.abs(jy - ix) / (jy * 512);
         //particles[i][j] = new IntegralOperation(-ax, ay, offz - r2);//
         //particles[i][j]=new IntegralOperation(a, b, normal);
         //ns[i][j] = new NumberSequance(new double[]{0.0});
         //particles[i][j].SetVariables(ns[i][j]);
         //particles[i][j].OperateUsing((OneStateFunction) this);//scale function
         //particles[i][j].iterate = (x) -> (x / Particle.b);//scaler
         //particles[i][j].resultant = (x) -> (4.5 / (x * 4.0 / 3.0));
         }
         }*/
        double pb[][] = new double[LandScape.LandSize][LandScape.LandSize];
        //for (int k = 0; k < 5; k++) {

        //h-=0.01; 
        for (int i = 0; i < LandScape.LandSize; i += lodStep) {
            double ix = i;//+ (ox * LandScape.LandSize);
            float ci = (float) (ix - LandScape.LandMiddle) / LandScape.LandSize;
            for (int j = 0; j < LandScape.LandSize; j += lodStep) {
                double jy = j;//+ (oy * LandScape.LandSize);
                float cj = (float) (jy - LandScape.LandMiddle) / LandScape.LandSize;
                float r2 = (float) (ci * ci + cj * cj);//-n/512.0
                float r3 = (float) 1.0f / (float) Math.sqrt(0.5 + r2);
                //particles[i][j].LoopSum(-r2 * zoom - start);
                //System.out.println("RESULT "+particles[i][j].results.length);
                //if(particles[i][j].results.length>0){
                //double ac = particles[i][j].results.First() * h;
                double ac = Random.Next(noi) - Random.Next(noi);
                particleSum[i][j] += (ac * r3);
                //}
                //System.out.println("S1 "+particleSum[i][j]);
            }
        }
        float ac = 1;
        //n+=1.0/n;
        for (int i = 0; i < LandScape.LandSize; i += lodStep) {
            double ix = i;//+ (ox * LandScape.LandSize);
            float ci = (float) (ix - LandScape.LandMiddle) / LandScape.LandSize;
            for (int j = 0; j < LandScape.LandSize; j += 4) {
                double jy = j;// +(oy * LandScape.LandSize);
                float cj = (float) (jy - LandScape.LandMiddle) / LandScape.LandSize;
                float r2 = (float) (ci * ci + cj * cj);//-n/512.0
                float r3 = (float) 1.0f / (float) Math.sqrt(0.5 + r2);
                float r4 = r2 * Random.Next(r2);
                    //particles[i][j].LoopSum(-r4 * speed);
                //if(particles[i][j].results.length>0){

                //ac = (float) (particles[i][j].results.First() * h);//*512/n
                ac = Random.Next(noi) - Random.Next(noi);
                particleSum[i][j] += (ac * r3);
                //lac=ac*ac+h;
                    /*if (particleSum[i][j] > (h)) {
                 particleSum[i][j] = h;
                 particleSum[i][j] -= Random.Next((float) h * Random.Next(noi));
                 } else if (particleSum[i][j] < -(h)) {
                 particleSum[i][j] = -h;
                 particleSum[i][j] += Random.Next((float) h * Random.Next(noi));
                 }*/
            }
            //System.out.println("S2 "+particleSum[i][j]);
            //particleSum[i][j]*=noise(i,j);
        }
        //}
        //}
        //NOISE
        for (int i = lodStep; i < LandScape.LandSize; i += lodStep) {
            for (int j = lodStep; j < LandScape.LandSize; j += lodStep) {
                pb[i][j] = particleSum[i][j] * Random.Next(noi) + particleSum[i - lodStep][j] * Random.Next(noi) + particleSum[i - lodStep][j - lodStep] * Random.Next(noi) + particleSum[i][j - lodStep] * Random.Next(noi);
            }
        }
        for (int i = lodStep; i < LandScape.LandSize; i += lodStep) {
            for (int j = lodStep; j < LandScape.LandSize; j += lodStep) {
                particleSum[i][j] = pb[i][j] * 0.25;//*pb[i][j]-pb[i][j]*h;
                //particleSum[i][j]*=0.25;//5*abs(sin((float)((i-256)*random(noi))-((j-256)*random(noi))*PI));//(0.5+random(0.125)-random(0.125));
            }
        }
        //FRACTAL
        for (int i = lodStep; i < LandScape.LandSize; i += lodStep) {
            for (int j = lodStep; j < LandScape.LandSize; j += lodStep) {
                pb[i][j] = (particleSum[i][j] + particleSum[i - lodStep][j] + particleSum[i - lodStep][j - lodStep] + particleSum[i][j - lodStep]) * 0.5;
            }
        }
        for (int i = lodStep; i < LandScape.LandSize; i += lodStep) {
            double ix = i;
            float ci = (float) (ix - LandScape.LandMiddle) / (LandScape.LandMiddle >> 1);
            for (int j = lodStep; j < LandScape.LandSize; j += lodStep) {
                double jy = j;
                float cj = (float) (jy - LandScape.LandMiddle) / (LandScape.LandMiddle >> 1);
                float r2 = (float) (ci * ci + cj * cj);//-n/512.0
                particleSum[i][j] += pb[i][j] * pb[i][j] + pb[i][j] - r2 * depth;
                particleSum[i][j] /= (0.5 + r2 * flatness);
                /*if (particleSum[i][j] > (h)) {
                 particleSum[i][j] = h;
                 particleSum[i][j] -= Random.Next((float) h * 0.5f);
                 } else if (particleSum[i][j] < -(h)) {
                 particleSum[i][j] = -h;
                 particleSum[i][j] += Random.Next((float) h * 0.5f);
                 }*/
            }
        }
        //SMOOTH
        for (int i = lodStep; i < LandScape.LandSize; i += lodStep) {
            for (int j = lodStep; j < LandScape.LandSize; j += lodStep) {
                pb[i][j] = particleSum[i][j] + particleSum[i - lodStep][j] + particleSum[i - lodStep][j - lodStep] + particleSum[i][j - lodStep];
            }
        }
        for (int i = lodStep; i < LandScape.LandSize; i += lodStep) {
            for (int j = lodStep; j < LandScape.LandSize; j += lodStep) {
                particleSum[i][j] = pb[i][j];
                particleSum[i][j] *= 0.25;//(0.5+random(0.125)-random(0.125));
            }
        }
        //SMOOTH
        double globalflatnessr = globalflatness * flatness;
        double globaldepthr = globaldepth * depth;
        double globalhr = globalh * h;
        for (int i = lodStep; i < LandScape.LandSize; i += lodStep) {
            for (int j = lodStep; j < LandScape.LandSize; j += lodStep) {
                pb[i][j] = particleSum[i][j] + particleSum[i - lodStep][j] + particleSum[i - lodStep][j - lodStep] + particleSum[i][j - lodStep];
            }
        }
        for (int i = lodStep; i < LandScape.LandSize; i += lodStep) {
            double ix = i;//+ (ox * LandScape.LandSize);
            float ci = (float) (ix - LandScape.LandMiddle) / LandScape.LandSize;
            for (int j = lodStep; j < LandScape.LandSize; j += lodStep) {

                double jy = j;// + (oy * LandScape.LandSize);
                float cj = (float) (jy - LandScape.LandMiddle) / LandScape.LandSize;
                float r2 = (float) (ci * ci + cj * cj);//-n/512.0
                //float r3 = (float) 1.0f / (float) Math.sqrt(0.5 + r2);
                //float r4 = r2 * Random.Next(r2);
                particleSum[i][j] += pb[i][j];
                particleSum[i][j] += globalhr;
                particleSum[i][j] -= globaldepthr * r2;
                particleSum[i][j] *= 0.5 / (1 + r2 + globalflatness);
                //particleSum[i][j] *= 0.25/globalflatnessr;//(0.5+random(0.125)-random(0.125));
            }
        }
        //SMOOTH
        for (int i = lodStep; i < LandScape.LandSize; i += lodStep) {
            for (int j = lodStep; j < LandScape.LandSize; j += lodStep) {
                pb[i][j] = particleSum[i][j] + particleSum[i - lodStep][j] + particleSum[i - lodStep][j - lodStep] + particleSum[i][j - lodStep];
            }
        }
        for (int i = lodStep; i < LandScape.LandSize; i += lodStep) {
            for (int j = lodStep; j < LandScape.LandSize; j += lodStep) {
                particleSum[i][j] = pb[i][j];
                particleSum[i][j] *= 0.25;//(0.5+random(0.125)-random(0.125));
            }
        }
        //SMOOTH
        for (int i = lodStep; i < LandScape.LandSize; i += lodStep) {
            for (int j = lodStep; j < LandScape.LandSize; j += lodStep) {
                pb[i][j] = particleSum[i][j] + particleSum[i - lodStep][j] + particleSum[i - lodStep][j - lodStep] + particleSum[i][j - lodStep];
            }
        }
        for (int i = lodStep; i < LandScape.LandSize; i += lodStep) {
            for (int j = lodStep; j < LandScape.LandSize; j += lodStep) {
                particleSum[i][j] = pb[i][j];
                particleSum[i][j] *= 0.25;//(0.5+random(0.125)-random(0.125));
            }
        }
    }

    LandScapeSection(SmallPlanet plan,int lod, double globaldepth, double globalflatness, double globalh, Molecula mx, Molecula my, double of64) {
        if (lod > 10) {
            lod = 10;
        }
        this.lod = lod;
        lodStep = (LandScape.LandSize >> lod);
        if (lodStep < 1) {
            lodStep = 1;
        }
        if (lodStep > LandScape.LandMiddle >> 1) {
            lodStep = LandScape.LandMiddle >> 1;
        }
//  
        //mx.SingleCalc();
        //my.SingleCalc();
        //ox = mx.cosine * (0.5f) - mx.sine * (0.5f);//-0.999//quantum haze (matter animatter)
        //oy = my.cosine * (0.5f) - my.sine * (0.5f);
        //mx.SingleCalc();
        // my.SingleCalc();

        //start = mx.cosine * (25) - mx.sine * (25);//controls hole size(-10)
        //offz = my.cosine * (25) - my.sine * (25);//controls curve(-10)
        // mx.SingleCalc();
        // my.SingleCalc();
        //zoom = 1.732e2 + Math.abs(mx.cosine) * (3e2f);//e4;
        //speed = 1.732e2 + Math.abs(my.cosine) * (3e2f);
        mx.SingleCalc();
        my.SingleCalc();
        h = 0;
        if (of64 > 0) {
            h = (64.0 + Math.abs(my.sine) + Math.abs(mx.sine)) / of64;
        }
        if (h < 0) {
            h = 0;
        }
        mx.SingleCalc();
        my.SingleCalc();
        flatness = (Math.abs(my.cosine) + Math.abs(mx.sine)) * 0.5;
        depth = (Math.abs(mx.cosine) + Math.abs(my.sine)) * 0.5;
        mx.SingleCalc();
        my.SingleCalc();
        noi = (float) h + (float) (Math.abs(my.sine * mx.sine) * 6);

        flatness = globalflatness * flatness*(plan.gravity/PlanetMap.PISqrd);
        depth = globaldepth * depth/(plan.atmospheres*plan.water);
        h = globalh * h/(plan.gravity/PlanetMap.PISqrd);
        //particles = new IntegralOperation[512][512];
        //ns = new NumberSequance[512][512];
        particleSum = new double[LandScape.LandSize][LandScape.LandSize];
        /*for (int i = 0; i < 512; i++) {
         double ix = i + (ox * 512.0);
         double ci = (double) (ix - 256) / 512;
         for (int j = 0; j < 512; j++) {
         double jy = j + (oy * 512.0);
         double cj = (double) (jy - 256) / 512;
         double r2 = (double) (ci * ci + cj * cj);//-n/512.0
         double ax = (double) Math.abs(ix - jy) / (jy * 512);
         double ay = (double) Math.abs(jy - ix) / (jy * 512);
         //particles[i][j] = new IntegralOperation(-ax, ay, offz - r2);//
         //particles[i][j]=new IntegralOperation(a, b, normal);
         //ns[i][j] = new NumberSequance(new double[]{0.0});
         //particles[i][j].SetVariables(ns[i][j]);
         //particles[i][j].OperateUsing((OneStateFunction) this);//scale function
         //particles[i][j].iterate = (x) -> (x / Particle.b);//scaler
         //particles[i][j].resultant = (x) -> (4.5 / (x * 4.0 / 3.0));
         }
         }*/
        double pb[][] = new double[LandScape.LandSize][LandScape.LandSize];
        //for (int k = 0; k < 5; k++) {

        //mx.SingleCalc();
        //my.SingleCalc();
        //ox = mx.cosine * (0.5f) - mx.sine * (0.5f);//-0.999//quantum haze (matter animatter)
        //oy = my.cosine * (0.5f) - my.sine * (0.5f);
//
        //h-=0.01; 
        for (int i = 0; i < LandScape.LandSize; i += lodStep) {
            double ix = i;//+ (ox * LandScape.LandSize);
            float ci = (float) (ix - LandScape.LandMiddle) / LandScape.LandSize;
            for (int j = 0; j < LandScape.LandSize; j += lodStep) {
                double jy = j;//+ (oy * LandScape.LandSize);
                float cj = (float) (jy - LandScape.LandMiddle) / LandScape.LandSize;
                float r2 = (float) (ci * ci + cj * cj);//-n/512.0
                float r3 = (float) 1.0f / (float) Math.sqrt(0.5 + r2);
                //particles[i][j].LoopSum(-r2 * zoom - start);
                //System.out.println("RESULT "+particles[i][j].results.length);
                //if(particles[i][j].results.length>0){
                //double ac = particles[i][j].results.First() * h;

                mx.SingleCalc();
                my.SingleCalc();
                double ac = h * Math.abs(mx.cosine) * (noi) - h * Math.abs(my.sine) * (noi);
                particleSum[i][j] += (ac * r3);
                //}
                //System.out.println("S1 "+particleSum[i][j]);
            }
        }
        double ac = 1;
        //n+=1.0/n;
        for (int i = 0; i < LandScape.LandSize; i += lodStep) {
            double ix = i;//+ (ox * LandScape.LandSize);
            float ci = (float) (ix - LandScape.LandMiddle) / LandScape.LandSize;
            for (int j = 0; j < LandScape.LandSize; j += 4) {
                double jy = j;//+ (oy * LandScape.LandSize);
                float cj = (float) (jy - LandScape.LandMiddle) / LandScape.LandSize;
                float r2 = (float) (ci * ci + cj * cj);//-n/512.0
                float r3 = (float) 1.0f / (1.0f + r2);
                //float r4 = r2 * Random.Next(r2);
                //particles[i][j].LoopSum(-r4 * speed);
                //if(particles[i][j].results.length>0){

                mx.SingleCalc();
                my.SingleCalc();
                //ac = (float) (particles[i][j].results.First() * h);//*512/n
                ac = (h * Math.abs(mx.cosine) * (noi) + h * Math.abs(my.sine) * (noi));
                particleSum[i][j] += (ac * r3);
                //lac=ac*ac+h;
                    /*if (particleSum[i][j] > (h)) {
                 particleSum[i][j] = h;
                 particleSum[i][j] -= Random.Next((float) h * Random.Next(noi));
                 } else if (particleSum[i][j] < -(h)) {
                 particleSum[i][j] = -h;
                 particleSum[i][j] += Random.Next((float) h * Random.Next(noi));
                 }*/
            }
            //System.out.println("S2 "+particleSum[i][j]);
            //particleSum[i][j]*=noise(i,j);
        }
        //}
        //}
        //NOISE
        for (int i = lodStep; i < LandScape.LandSize; i += lodStep) {
            for (int j = lodStep; j < LandScape.LandSize; j += lodStep) {
                mx.SingleCalc();
                my.SingleCalc();
                //ac = (float) (particles[i][j].results.First() * h);//*512/n

                pb[i][j] = h * (particleSum[i][j] * Math.abs(mx.cosine) * (noi) + particleSum[i - lodStep][j] * Math.abs(my.sine) * (noi) + particleSum[i - lodStep][j - lodStep] * Math.abs(mx.sine) * (noi) + particleSum[i][j - lodStep] * Math.abs(my.cosine) * (noi));
            }
        }
        for (int i = lodStep; i < LandScape.LandSize; i += lodStep) {
            double ix = i;//+ (ox * LandScape.LandSize);
            float ci = (float) (ix - LandScape.LandMiddle) / LandScape.LandSize;
            for (int j = lodStep; j < LandScape.LandSize; j += lodStep) {
                double jy = j;//+ (oy * LandScape.LandSize);
                float cj = (float) (jy - LandScape.LandMiddle) / LandScape.LandSize;
                float r2 = (float) (ci * ci + cj * cj);//-n/512.0
                float r3 = (float) 1.0f / (1.0f + r2);
                particleSum[i][j] = pb[i][j] * 0.25 * flatness * r3 - depth * h * r2;//*pb[i][j]-pb[i][j]*h;

                //particleSum[i][j]*=0.25;//5*abs(sin((float)((i-256)*random(noi))-((j-256)*random(noi))*PI));//(0.5+random(0.125)-random(0.125));
            }
        }
        //FRACTAL
        /*for (int i = lodStep; i < LandScape.LandSize; i += lodStep) {
         for (int j = lodStep; j < LandScape.LandSize; j += lodStep) {
         pb[i][j] = (particleSum[i][j] + particleSum[i - lodStep][j] + particleSum[i - lodStep][j - lodStep] + particleSum[i][j - lodStep]);
         }
         }
         for (int i = lodStep; i < LandScape.LandSize; i += lodStep) {
         double ix = i;
         float ci = (float) (ix - LandScape.LandMiddle) / (LandScape.LandMiddle >> 1);
         for (int j = lodStep; j < LandScape.LandSize; j += lodStep) {
         double jy = j;
         float cj = (float) (jy - LandScape.LandMiddle) / (LandScape.LandMiddle >> 1);
         float r2 = (float) (ci * ci + cj * cj);//-n/512.0
         particleSum[i][j]+= pb[i][j] - depth/(1.0+r2);
         particleSum[i][j]*=flatness/(1.0+r2);
         particleSum[i][j]+= pb[i][j];
         particleSum[i][j]*=0.25;
         }
         }
         //SMOOTH
         for (int i = lodStep; i < LandScape.LandSize; i += lodStep) {
         for (int j = lodStep; j < LandScape.LandSize; j += lodStep) {
         pb[i][j] = particleSum[i][j] + particleSum[i - lodStep][j] + particleSum[i - lodStep][j - lodStep] + particleSum[i][j - lodStep];
         }
         }
         for (int i = lodStep; i < LandScape.LandSize; i += lodStep) {
         for (int j = lodStep; j < LandScape.LandSize; j += lodStep) {
         particleSum[i][j] = pb[i][j];
         particleSum[i][j] *= 0.25;//(0.5+random(0.125)-random(0.125));
         }
         }*/
        //SMOOTH
        /*for (int i = lodStep; i < LandScape.LandSize; i += lodStep) {
         for (int j = lodStep; j < LandScape.LandSize; j += lodStep) {
         pb[i][j] = particleSum[i][j] + particleSum[i - lodStep][j] + particleSum[i - lodStep][j - lodStep] + particleSum[i][j - lodStep];
         }
         }
         for (int i = lodStep; i < LandScape.LandSize; i += lodStep) {
         double ix = i ;//+ (ox * LandScape.LandSize);
         float ci = (float) (ix - LandScape.LandMiddle) / LandScape.LandSize;
         for (int j = lodStep; j < LandScape.LandSize; j += lodStep) {

         double jy = j ;//+ (oy * LandScape.LandSize);
         float cj = (float) (jy - LandScape.LandMiddle) / LandScape.LandSize;
         float r2 = (float) (ci * ci + cj * cj);//-n/512.0
         float r3 = (float) (r2)/(float)(1+h);//-n/512.0
         //float r3 = (float) 1.0f / (float) Math.sqrt(0.5 + r2);
         //float r4 = r2 * Random.Next(r2);
         particleSum[i][j] += h*pb[i][j];
         particleSum[i][j] += h*r3;
         particleSum[i][j] -= h*depth*r3;
         particleSum[i][j] *= 0.5 / (1 + r3 + flatness);
                
                
         //particleSum[i][j] *= 0.25/globalflatnessr;//(0.5+random(0.125)-random(0.125));
         }
         }*/
        //SMOOTH
        for (int i = lodStep; i < LandScape.LandSize; i += lodStep) {
            for (int j = lodStep; j < LandScape.LandSize; j += lodStep) {
                pb[i][j] = particleSum[i][j] + particleSum[i - lodStep][j] + particleSum[i - lodStep][j - lodStep] + particleSum[i][j - lodStep];
            }
        }
        for (int i = lodStep; i < LandScape.LandSize; i += lodStep) {
            for (int j = lodStep; j < LandScape.LandSize; j += lodStep) {
                particleSum[i][j] = pb[i][j];
                particleSum[i][j] *= 0.25;//(0.5+random(0.125)-random(0.125));
            }
        }
        //SMOOTH
        for (int i = lodStep; i < LandScape.LandSize; i += lodStep) {
            for (int j = lodStep; j < LandScape.LandSize; j += lodStep) {

                pb[i][j] = particleSum[i][j] + particleSum[i - lodStep][j] + particleSum[i - lodStep][j - lodStep] + particleSum[i][j - lodStep];
            }
        }
        for (int i = lodStep; i < LandScape.LandSize; i += lodStep) {
            double ix = i;//+ (ox * LandScape.LandSize);
            float ci = (float) (ix - LandScape.LandMiddle) / LandScape.LandSize;
            for (int j = lodStep; j < LandScape.LandSize; j += lodStep) {
                double jy = j;//+ (oy * LandScape.LandSize);
                float cj = (float) (jy - LandScape.LandMiddle) / LandScape.LandSize;
                float r2 = (float) (ci * ci + cj * cj);//-n/512.0
                float r3 = (float) 1.0f / (1.0f + r2);
                particleSum[i][j] = pb[i][j] - pb[i][j] * depth * r2;
                particleSum[i][j] *= flatness * r3;//(0.5+random(0.125)-random(0.125));
            }
        }
    }
}

class Lerp {

    double accum;
    double at;
    double from = 0;
    double to = 0;
    double point = 0;

}

class Cloud extends IntegralOperation {

    double normal;
    double dim;
    double e = 4.564767917455;//compacted mass(TL)->4.564767917455 chaos? 0.29 Result 4.000000000000003 << 	0=-3.141592653589793
    RingList<Lerp> lerps = new RingList();

    Cloud() {
        super(-1000 * Random.NextDouble(), 1000 * Random.NextDouble(), 0);
        //a=1;//potential energy start (symetrical)
        //b=100.2;//half calc decay(100)
        e = Random.NextDouble();
        normal = -Random.NextDouble() * 20;//normalizing constant(fire) chaos if negative -e
        dim = 4.0 * Random.NextDouble();//4.0 sphere 2.0 circle 1.0 point
        NumberSequance ns = new NumberSequance(new Double[]{Random.NextDouble(), Random.NextDouble(), Random.NextDouble(), Random.NextDouble(), Random.NextDouble(), Random.NextDouble(), Random.NextDouble(), Random.NextDouble()});
        for (int i = 0; i < ns.Length(); i++) {
            Lerp lin = new Lerp();
            lin.accum = 10 * Random.NextDouble();
            lerps.Append(lin);
        }

        this.SetVariables(ns);
        this.OperateUsing((x) -> (1.0 / x - x * (1.0 - 1.0 / b)));//scale function
        this.iterate = (x) -> (x) / b;//scaler
        //in.resultant=(x)->e/(x*dim/3.0);//ratio scale (b/3)*0.5 (using inverse for explosion)//-1.4531450450773544	
        this.resultant = (x) -> e / (x * dim / 3.0);//ratio scale (b/3)*0.5 (using inverse for explosion)
        //
        this.LoopSum(0.1);//energy system
    }

    void Update(GameTime time, double limit, double airPreasure) {
        Node<Lerp> lerp = lerps.Start();
        Node<Double> res = results.Start();
        double et = time.animTime / (Physics.day * airPreasure);
        for (; lerp.data != null && res.data != null; res = res.next, lerp = lerp.next) {
            if (lerp.data.point == 0) {
                if (!Finalize()) {
                    this.LoopSum((Random.NextDouble() - Random.NextDouble()) * et);//energy system
                } else {
                    a = -100 * Random.NextDouble();
                    b = 100 * Random.NextDouble();

                    lerp.data.accum *= airPreasure * Random.NextDouble();
                    this.LoopSum((Random.NextDouble() - Random.NextDouble()) * et);//energy system

                }
                lerp.data.accum += res.data * Random.NextDouble();
                if (lerp.data.accum > limit || lerp.data.accum < -limit) {
                    lerp.data.accum *= airPreasure * Random.NextDouble();
                }
                lerp.data.accum *= 1.0 - airPreasure;
                lerp.data.to = lerp.data.accum;
                lerp.data.at = lerp.data.from;
                lerp.data.point += et;
            } else {
                if (lerp.data.point < 1.0) {
                    //System.out.println(" Operation "+point+" Lerp "+at);
                    lerp.data.at = Maths.lerp(lerp.data.point, lerp.data.from, lerp.data.to);
                    lerp.data.point += et;
                } else {
                    lerp.data.from = lerp.data.to;
                    lerp.data.point = 0;
                }
            }
            //System.out.println(time.gameTime/Physics.day+" Lerp At"+lerp.data.at+" Point"+lerp.data.point+" From"+lerp.data.from+" To"+lerp.data.to);
        }

    }
}

public class LandScape {

    public static final int LandSize = 256;
    public static final int LandMiddle = 128;
    public static int ScapeSize = 256;
    public static int ScapeMiddle = 128;
    LandScapeSection ls1;
    LandScapeSection ls2;
    LandScapeSection ls3;
    LandScapeSection ls4;

    boolean finished1 = false;
    boolean finished2 = false;
    boolean finished3 = false;
    boolean finished4 = false;
    int genstage = 0;
    int laststage = 0;
    TessaShape scape[][];
    byte land[][];
    float landHeight[];
    TessaShape buildings[];
    RingList<Base> elements[][];
    RingList<Base> bases = new RingList();
    RingList<Base> removeBases = new RingList();
    Fighter fighter;//

    RingList<Craft> missiles = new RingList();
    RingList<Craft> fighters = new RingList();
    RingList<Craft> nearFighters = new RingList();
    RingList<Craft> removeMissiles = new RingList();
    RingList<Craft> crashingCraft = new RingList();
    RingList<Craft> removeCraft = new RingList();
    RingList<ParticlePoint> explosions = new RingList();
    RingList<ParticlePoint> removeExplosions = new RingList();
    Base base;
    Cloud clouds[][];
    double windAngle = 0;
    Vector cloudOffset = new Vector(0, 0, 0);
    RingList<RingList<Tessa>> cloudTriangles = new RingList();
    RingList<Point> cloudPos = new RingList();
    RingList<RingList<Tessa>> cloudTriangleBuffer = new RingList();
    RingList<Point> cloudPosBuffer = new RingList();
    Molecula rnd1;
    Molecula rnd2;
    Molecula rnd3;
    Molecula rnd4;
    static float quad;
    static int lod;
    static int range = 0;
    static double quadR=1;
    static double quadRLod=1;
    double airPresure = 0.1;
    double windSpeed = 0.1;
    double globalDepth;
    double globalHeight;
    double globalFlatness;
    ScapeScreen screen;
    int x;
    int y;
    RingList<Light> suns = null;
    GImage map;
    Pigment color = new Pigment(1, 1, 1);

    LandScape(ScapeScreen screen, LoadFrame win) {
        this.screen = screen;
        lod = 4;

        quad = (float)(Math.PI*7.0*screen.scope.home.relative.radius);
        quadR = LandSize * quad;
        quadRLod = quadR * (lod + range);

        scape = new TessaShape[64][lod];

        landHeight = new float[64];
        airPresure =screen.scope.home.atmospheres/screen.scope.home.gravity;
        windSpeed =screen.scope.home.atmospheres/screen.scope.home.gravity*screen.scope.home.revolvesRound.relative.mass/screen.scope.home.orbital.radius;
        globalDepth = screen.scope.home.water/PlanetMap.PISqrd-0.25;
        globalHeight = 1.25-screen.scope.home.water/PlanetMap.PISqrd;
        globalFlatness = screen.scope.home.gravity/PlanetMap.PISqrd;
        rnd1=new Molecula(screen.scope.home.relative.mass,screen.scope.home.relative.radius,screen.scope.home.relative.density);
        rnd2=new Molecula(screen.scope.home.relative.mass*0.5,screen.scope.home.relative.radius*0.5,screen.scope.home.relative.density*0.5);
        rnd3=new Molecula(screen.scope.home.relative.mass*0.25,screen.scope.home.relative.radius*0.25,screen.scope.home.relative.density*0.25);
        rnd4=new Molecula(screen.scope.home.relative.mass*0.125,screen.scope.home.relative.radius*0.125,screen.scope.home.relative.density*0.125);
        this.color=new Pigment(screen.scope.home.color);
        //this.Scape1();
        //this.Scape2();
        //this.Scape3();
        //this.Scape4();
        Game.exec.execute(this::Scape1);
        Game.exec.execute(this::Scape2);
        while (!(finished1 && finished2)) {
            //System.out.println(" "+finished1+" "+finished2+" "+finished3+" "+finished4);
            if (genstage > laststage) {
                laststage = genstage;
                win.Add(1, "Generation Of Landscape");
            }
            win.invalidate();
        }
        Game.exec.execute(this::Scape3);
        Game.exec.execute(this::Scape4);
        while (!(finished3 && finished4)) {
            //System.out.println(" "+finished1+" "+finished2+" "+finished3+" "+finished4);
            if (genstage > laststage) {
                laststage = genstage;
                win.Add(1, "Generation Of Landscape");
            }
            win.invalidate();
        }
        //SlowHeightSort(landHeight, scape);

        CreateMap("Objects"+Game.SYSTEMDIR+"Earth.plan.txt", win,screen.scope.home.water);
        elements = new RingList[ScapeSize][ScapeSize];
        clouds = new Cloud[ScapeSize][ScapeSize];
        for (int i = 0; i < ScapeSize; i++) {
            for (int j = 0; j < ScapeSize; j++) {
                clouds[i][j] = new Cloud();
                elements[i][j] = new RingList();
            }

        }
        TessaShape bs = Game.ObjectLoader("Objects"+Game.SYSTEMDIR+"DEF1.obj", new Vector(0, 0, 0), new Pigment(0, 0, 255), new Pigment(0, 0, 255), new Pigment(0, 0, 255),5);
        
        //TessaShape.Normalize(bs,new Vector(10,10,10),new Vector(1,2,1),new Vector(2,1,2),new Vector(3,1,3));//space vessel
        TessaShape.NormalScale(bs,new Vector(1,1,1),new Vector(1,2,1),new Vector(2,1,2),new Vector(3,0.5,3),false,true);
        
        
        /*StellarFiles test=new StellarFiles();
        
        System.out.println("LOADING FILE");
        test.LoadFile("Test2.txt",false);
        TessaShape bs=new TessaShape();
        bs.From(test);*/
        if (bs != null) {
            //base=new Base(new Point(0,300,0), bs,new Vector(1,1,1),new Vector(Linear.PID2*0.5,0,Linear.PID2*0.5));//enemy
            base = new Base(new Point(0, 300, 0), bs, new Vector(2, 2, 2), null);//home millitary
            base.AddForceField(new ForceField(base,screen.scope.tech,1));
            elements[ScapeMiddle][ScapeMiddle].Append(base);
            bases.Append(base);
        }
        Lathe build = new Lathe();
        //int w, int h, float radius, float depth, float depthStep, float scale, float oscillate, int corrigate
        build.SetUpShapeWedge(3, 5, 2f, 0.1f, 0.1f, 0f, 0.25f, 1, 1, true);//ufo

        //build.SetUpShapeDisc(6, 6,1,3, 1,1f,0,0);witches hat
        //build.SetUpShapeDisc(13,6,3,0.1f, 1.0f,1f,3f,0);//ufo
        //build.SetUpShapePoly(3,3,0.1f,0.1f, 1f,1f,false,3);//missile
        //build.SetUpShapeCylinder(13,6,5f,0.1f,1f,0,1,true,0);//ufo
        //build.SetUpShapeCone(13,6,5f,0.1f,1f,0f,1f);//ufo
        //build.SetUpShapeWedge(13,6,5f,0.1f,1f,0f,1f,1,1,true);//ufo
        //build.SetUpShapeWedge(13,6,5f,0.1f,1f,0f,1f,10,10,true);//building
        //build.SetUpShapeRocket(6,13,1f,0.5f,1f,0f,1f,1,1,1,true,1);//tower
        //build.SetUpShapeEngine(6,6,1f,0.1f,0.1f,1);//tower
        RingList<Tessa> shape = Lathe.SetShape(10, false, true, 0, build, new Pigment(128, 255, 128));
        fighter = new Fighter(new Point(0, 0, 0), new TessaShape(shape), new Vector(0.125, 0.125, 0.125), null, this, 20);
        BoosterJet jet=new BoosterJet(fighter,screen.scope.tech,4);
        System.out.println("Jet "+jet.mass.Value());
        System.out.println("Fighter "+fighter.mass.Value());
        fighter.AddBooster(jet);
        this.fighters.Append(fighter);
        //
        ConstructBuildings(rnd1);
        Population populus=new Population();
        populus.adult=new People();
        populus.adult.number=100000;
        ConstructCities(populus,rnd2,win);
        ScapeToMap();
        win.dispose();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Map Elements">
    void ConstructBuildings(Molecula rnd){
        this.buildings=new TessaShape[64];
        TessaShape building=Game.ObjectLoader("Objects"+Game.SYSTEMDIR+"Cube.obj", new Vector(0, 0, 0), new Pigment(128, 128, 255), new Pigment(255, 255, 255), new Pigment(0, 0, 128),1);
        double sstep=0.02;
        double ss=0.125+sstep;
        for(int i=0;i<64;i++){
        TessaShape build=new TessaShape(building);
        double y=((double)(i+1)*(rnd.NextDouble()))*ss;
        Vector scale=new Vector(32.0/(i+1)+(((double)(i+1)+(1+rnd.NextDouble()))/y)*ss,y,32.0/(i+1)+(((double)(i+1)+(1+rnd.NextDouble()))/y)*ss);
        Matrix m = new Matrix(new Vector(0,50,0), scale);
        TessaShape.Transform(build, m);
        this.buildings[i]=build;
        ss+=sstep;
        }
    }
    public void ConstructCities(Population planetPopulus,Molecula rnd,LoadFrame win){
        long pop=planetPopulus.adult.number;
        //
        int cx=0;int cy=0;
        int site=1+(int)(pop/ScapeSize);
        int i=0;
        boolean end=false;
        do{
            if(land[cx][cy]>0){
            boolean ok=rnd.NextInt(ScapeSize)==ScapeMiddle;
            if(ok){
            HumanCity base=new HumanCity(new Point((cx-ScapeMiddle)*quadR,0, (cy-ScapeMiddle)*quadR),rnd,pop/ScapeSize,buildings);
            base.AddForceField(new ForceField(base,screen.scope.tech,5));
            pop-=(long)(site);
            elements[cx][cy].Append(base);
            bases.Append(base);
            
            i++;
            win.Add(0,i+" City "+cx+" "+cy+" Population "+pop+" - "+site);
            }
            }
            cx++;
            if(cx>=ScapeSize){
                
            cx=0;
            cy++;
            if(cy>=ScapeSize){
            cy=0;
            end=true;
            }
            }
            
        }while(pop>0 && !end);
            win.Add(1,"Cities Complete");
    }
    public static String[] ReadAllLines(String fileName) {
        String dir = System.getProperty("user.dir");
        System.out.println("LOADING:" + dir + Game.SYSTEMDIR + fileName + "\n");
        // java.io.File file=new java.io.File(dir,"Cube.obj");
        java.io.File file = new java.io.File(dir, fileName);
        if (file.exists()) {
            java.util.ArrayList<java.lang.StringBuilder> strings = new java.util.ArrayList<>();
            java.lang.StringBuilder build = new java.lang.StringBuilder();
            try {
                java.io.FileReader reader = new java.io.FileReader(file);
                int c = reader.read();
                while (c >= 0) {
                    if (c == '\n') {
                        strings.add(build);
                        build = new StringBuilder();
                        //System.out.print("[loaded]");
                    } else {
                        build.append((char) c);
                        //System.out.print((char)c);
                    }
                    c = reader.read();
                }
            } catch (Exception iox) {

            }
            if (strings.size() > 0) {
                String array[] = new String[strings.size()];
                for (int i = 0; i < strings.size(); i++) {
                    array[i] = strings.get(i).toString();
                }
                return array;
            }
            System.out.println("LOADING:" + "FAILED" + dir + "\\" + fileName + "\n");
            return null;
        }
        System.out.println("LOADING:" + "FILE NOT FOUND" + dir + "\\" + fileName + "\n");
        return null;
    }

    public static byte[][] LoadWorldPlan(String file, Local size) {
        System.out.println("Loading World Map from:"+file);
        String[] planet = ReadAllLines(file);
        byte map[][] = null;//new byte[256][128];
        if (planet != null) {

            int planetWidth = 128;
            int planetHeight = 64;
            int from = 0;
            boolean next = false;
            boolean finished = false;
            for (; from < planet.length; from++) {

                if (!planet[from].startsWith("#")) {
                    StringBuilder dig = new StringBuilder();
                    for (int c = 0; c < planet[from].length(); c++) {
                        if (Character.isDigit(planet[from].charAt(c))) {
                            while (Character.isDigit(planet[from].charAt(c))) {
                                dig.append(planet[from].charAt(c));
                                c++;
                            }
                            c--;
                            if (!next) {
                                planetWidth = Integer.parseInt(dig.toString());
                                dig = new StringBuilder();
                                next = true;
                            } else {
                                planetHeight = Integer.parseInt(dig.toString());
                                finished = true;
                                break;
                            }
                        }
                    }

                }
                if (finished) {
                    break;
                }
            }
            System.out.print("w " + planetWidth + "h " + planetHeight);
            size.x = planetWidth;
            size.y = planetHeight;
            //System.out.println("MAP SIZE :"+size.toString()); 
            map = new byte[planetWidth][planetHeight];
            int x = 0;
            int y = 0;
            for (int i = from; i < planet.length; i++) {

                if (!planet[i].startsWith("#")) {
                    if (planet[i].length() > 0) {
                        boolean start = false;
                        for (int c = 0; c < planet[i].length(); c++) {
                            if (planet[i].charAt(c) == '{') {
                                start = true;
                                c++;
                            }
                            if (start && Character.isDigit(planet[i].charAt(c))) {
                                byte a = Byte.parseByte("" + planet[i].charAt(c));
                                map[x][y] = a;
                                System.out.print(a);
                                x++;
                            } else if (start && planet[i].charAt(c) == '}') {
                                start = false;
                                x = 0;
                                System.out.println(" x " + x + " y " + y);
                                y++;

                            }
                        }

                    }
                }
            }
        }
        return map;
    }

    void CreateMap(String planetMapFile, LoadFrame win,double water) {
        Local size = new Local();
        win.Add(1, "Loading Map");
        byte map[][] = LoadWorldPlan(planetMapFile, size);
        ScapeSize = size.x * 2;
        ScapeMiddle = size.y * 2;
        land = new byte[ScapeSize][ScapeSize];
        int addwater=(int)(water*0.5);
        int w=(int)(water*2);
        Molecula m1 = rnd1;
        Molecula m2 = rnd2;
        win.Add(1, "Building Map");
        for (int i = 0; i < ScapeSize; i++) {
            for (int j = 0; j < ScapeSize; j++) {
                int add = ((map[i >> 1][j >> 2] - addwater) * w) - 8;
                if (add > 0) {
                    m1.SingleCalc();
                    m2.SingleCalc();
                    land[i][j] = (byte) add;
                    land[i][j] += Math.abs(m1.cosine + m1.sine + m2.cosine + m2.sine) * 8;
                    if (land[i][j] > 63) {
                        land[i][j] = 63;
                    }
                }
            }
        }
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Set Up">

    void CreateMap(LoadFrame win) {
        land = new byte[ScapeSize][ScapeSize];
        for (int i = 0; i < ScapeSize; i++) {
            for (int j = 0; j < ScapeSize; j++) {
                land[i][j] = (byte) (Random.Next(16) - Random.Next(16));
            }
        }
        Molecula m1 = new Molecula();
        Molecula m2 = new Molecula();
        for (int k = 1; k < 5; k++) {
            win.Add(1, "Building Map");
            m1 = new Molecula();
            m2 = new Molecula();
            double mx = Random.Next(ScapeSize);
            double my = Random.Next(ScapeSize);
            for (int i = 0; i < 65536; i++) {
                m1.SingleCalc();
                mx += m1.cosine;
                my += m1.sine;
                m2.SingleCalc();
                if (mx >= ScapeSize) {
                    mx = 0;
                } else if (mx < 0) {
                    mx = ScapeSize - 1;
                }
                if (my > ScapeSize) {
                    my = 0;
                } else if (my < 0) {
                    my = ScapeSize - 1;
                }
                land[(int) mx][(int) my] += ((int) Math.abs(m2.cosine * 2 + m2.sine * 2));
            }
        }
        for (int k = 1; k < 5; k++) {
            win.Add(1, "Building Map");
            m1 = new Molecula();
            m2 = new Molecula();
            double mx = Random.Next(ScapeSize);
            double my = Random.Next(ScapeSize);
            for (int i = 0; i < 65536; i++) {
                m1.SingleCalc();
                mx += m1.cosine;
                my += m1.sine;
                m2.SingleCalc();
                if (mx >= ScapeSize) {
                    mx = 0;
                } else if (mx < 0) {
                    mx = ScapeSize - 1;
                }
                if (my > ScapeSize) {
                    my = 0;
                } else if (my < 0) {
                    my = ScapeSize - 1;
                }
                land[(int) mx][(int) my] += ((int) Math.abs(m2.cosine * 2 + m2.sine * 2));
            }
        }
        //for(int k=1;k<3;k++){
        int k = 8;
        win.Add(1, "Building Map Please Wait..");
        int buffer[][] = new int[ScapeSize][ScapeSize];

        Molecula m = new Molecula();
        /*for (int i = k; i < 512-k; i++) {
         for (int j = k; j < 512-k; j++) {
         m.SingleCalc();
         buffer[i][j]=(int)(m.cosine*4+m.sine*4);
         int k1=Random.Next(k);
         int k2=Random.Next(k);
         int k3=Random.Next(k);
         int k4=Random.Next(k);
         for(int lx=-k1;lx<=k2;lx++){
         for(int ly=-k3;ly<=k4;ly++){
                        
         buffer[i][j]+=land[i+lx][j+ly]/(1+Math.abs(lx)+Math.abs(ly));
         }}
         }
         }*/

        for (int i = 1; i < ScapeSize - 1; i++) {
            for (int j = 1; j < ScapeSize - 1; j++) {
                m.SingleCalc();
                buffer[i][j] = (int) (m.cosine * 2 + m.sine * 2);
                for (int lx = -1; lx <= 1; lx++) {
                    for (int ly = -1; ly <= 1; ly++) {

                        buffer[i][j] += land[i + lx][j + ly] / (1 + Math.abs(lx) + Math.abs(ly));
                    }
                }
            }
        }
        k = 32;
        for (int i = 0; i < ScapeSize; i++) {
            for (int j = 0; j < ScapeSize; j++) {
                land[i][j] += (byte) ((buffer[i][j] + Random.Next(k) - Random.Next(k)) >> 1);
                if (land[i][j] > 64) {
                    land[i][j] = (byte) ((land[i][j] - Random.Next(k)) >> 1);
                }
                if (land[i][j] < 64) {
                    land[i][j] = (byte) ((land[i][j] + Random.Next(k)) >> 1);
                }
            }
        }
        for (k = 1; k < 5; k++) {
            win.Add(1, "Building Map");
            m1 = new Molecula();
            m2 = new Molecula();
            double mx = Random.Next(ScapeSize);
            double my = Random.Next(ScapeSize);
            for (int i = 0; i < 65536; i++) {
                m1.SingleCalc();
                mx += m1.cosine;
                my += m1.sine;
                m2.SingleCalc();
                if (mx >= ScapeSize) {
                    mx = 0;
                } else if (mx < 0) {
                    mx = ScapeSize - 1;
                }
                if (my > ScapeSize) {
                    my = 0;
                } else if (my < 0) {
                    my = ScapeSize - 1;
                }
                land[(int) mx][(int) my] += ((int) Math.abs(m2.cosine * 2 + m2.sine * 2));
            }
        }
        for (k = 1; k < 7; k++) {
            win.Add(1, "Building Map");
            m1 = new Molecula();
            m2 = new Molecula();
            double mx = Random.Next(ScapeSize);
            double my = Random.Next(ScapeSize);
            for (int i = 0; i < 65536; i++) {
                m1.SingleCalc();
                mx += m1.cosine;
                my += m1.sine;
                m2.SingleCalc();
                if (mx >= ScapeSize) {
                    mx = 0;
                } else if (mx < 0) {
                    mx = ScapeSize - 1;
                }
                if (my > ScapeSize) {
                    my = 0;
                } else if (my < 0) {
                    my = ScapeSize - 1;
                }

                if (land[(int) mx][(int) my] > 32) {
                    land[(int) mx][(int) my] -= ((int) Math.abs(m2.cosine * 7 + m2.sine * 7));
                } else {

                    land[(int) mx][(int) my] += ((int) Math.abs(m2.cosine * 7 + m2.sine * 7));
                }
            }
        }
        /*k=2;
         for (int i = 1; i < 511; i++) {
         for (int j = 1; j < 511; j++) {
         m.SingleCalc();
         buffer[i][j]=(int)(m.cosine*2+m.sine*2);
         for(int lx=-1;lx<=1;lx++){
         for(int ly=-1;ly<=1;ly++){
                        
         buffer[i][j]+=land[i+lx][j+ly]/(1+Math.abs(lx)+Math.abs(ly));
         }}
         }
         }
         for (int i = 0; i < 512; i++) {
         for (int j = 0; j < 512; j++) {
         land[i][j]+=(byte)((buffer[i][j]+Random.Next(k)-Random.Next(k))>>1);
         if(land[i][j]>32){land[i][j]=(byte)((land[i][j]+Random.Next(k))>>1);}
         if(land[i][j]<32){land[i][j]=(byte)((land[i][j]-Random.Next(k))>>1);}
         }
         }
         //}*/
        /*k=16;
         for (int i = k; i < ScapeSize-k; i++) {
         for (int j = k; j < ScapeSize-k; j++) {
         buffer[i][j]=land[i][j];
         for(int lx=-k;lx<=k;lx++){
         for(int ly=-k;ly<=k;ly++){
         buffer[i][j]+=land[i+lx][j+ly]/(1+Math.abs(lx)+Math.abs(ly));
         }}
         }
         }
        
        
         int n=k+2;
         for (int i = 0; i < ScapeSize; i++) {
         for (int j = 0; j < ScapeSize; j++) {
         land[i][j]=(byte)((land[i][j]+buffer[i][j]/(n))>>1);
         }
         }*/
        for (k = 1; k < 17; k++) {
            win.Add(1, "Building Map");
            m1 = new Molecula();
            m2 = new Molecula();
            double mx = Random.Next(ScapeSize);
            double my = Random.Next(ScapeSize);
            double sc = 0.1 / k;
            for (int i = 0; i < 262144; i++) {
                m1.SingleCalc(sc);
                if (k % 3 == 0) {
                    mx += m1.cosine + (Random.NextDouble() - Random.NextDouble()) * 0.25;
                    my += m1.sine + (Random.NextDouble() - Random.NextDouble()) * 0.25;
                } else {
                    mx += m1.cosine;
                    my += m1.sine;

                }
                m2.SingleCalc();
                if (mx >= ScapeSize) {
                    mx = 0;
                } else if (mx < 0) {
                    mx = ScapeSize - 1;
                }
                if (my > ScapeSize) {
                    my = 0;
                } else if (my < 0) {
                    my = ScapeSize - 1;
                }
                if (land[(int) mx][(int) my] > 64) {
                    land[(int) mx][(int) my] -= ((int) Math.abs(m2.cosine * 3 + m2.sine * 3));
                } else {

                    land[(int) mx][(int) my] += ((int) Math.abs(m2.cosine * 3 + m2.sine * 3));
                }
            }
        }

        for (k = 1; k < 9; k++) {
            win.Add(1, "Building Map");
            m1 = new Molecula();
            double mx = Random.Next(ScapeSize);
            double my = Random.Next(ScapeSize);
            double sc = 0.1 / k;
            for (int i = 0; i < 65536; i++) {
                m1.SingleCalc(sc);
                if (k % 3 == 0) {
                    mx += m1.cosine + (Random.NextDouble() - Random.NextDouble()) * 0.25;
                    my += m1.sine + (Random.NextDouble() - Random.NextDouble()) * 0.25;
                } else {
                    mx += m1.cosine;
                    my += m1.sine;

                }
                if (mx >= ScapeSize) {
                    mx = 0;
                } else if (mx < 0) {
                    mx = ScapeSize - 1;
                }
                if (my > ScapeSize) {
                    my = 0;
                } else if (my < 0) {
                    my = ScapeSize - 1;
                }
                land[(int) mx][(int) my] = (byte) ((land[(int) mx][(int) my] >> 1) + Random.Next(3) - Random.Next(3));
            }
        }
    }

    void ScapeToMap() {
        map = new GImage(ScapeSize, ScapeSize);
        for (int i = 0; i < ScapeSize; i++) {
            for (int j = 0; j < ScapeSize; j++) {
                if (land[i][j] < 0) {
                    land[i][j] = 0;
                }
                if (land[i][j] > 63) {
                    land[i][j] -= Random.Next(32);
                }
                if (land[i][j] < 0) {
                    land[i][j] = 0;
                }

                if (land[i][j] > 63) {
                    land[i][j] = 63;
                }
                if(elements[i][j]!=null && elements[i][j].Length()>0){
                    if(elements[i][j].First() instanceof HumanCity){
                        map.SetPixel(i, j, 255, 255, 255,255);
                    }else{
                    if(elements[i][j].First() instanceof Base){
                        map.SetPixel(i, j, 255, 255, 0,255);
                    }
                    }
                }else{
                    
                        map.SetPixel(i, j, 255, land[i][j] * 2, 32 + land[i][j], Math.abs(32 - land[i][j]) * 2);
                    }
                }
            
        }
    }

    static int HeightSort(float a, float b) {
        if (a < b) {
            return -1;
        } else if (a > b) {
            return 1;
        }
        return 0;
    }

    void SlowHeightSort(float[] unsortedArray,
            TessaShape unsortedList[][]) {

        for (int i = 0; i < unsortedList.length - 1; i++) {
            int mini = i;
            for (int j = i + 1; j < unsortedList.length; j++) {
                if (HeightSort(unsortedArray[j], unsortedArray[i]) == 1) {
                    mini = j;
                }
                if (mini != i) {
                    for (int k = 0; k < lod; k++) {
                        RingList<Tessa> d = unsortedList[mini][k].list;
                        unsortedList[mini][k] = unsortedList[i][k];
                        unsortedList[i][k].list = d;
                    }
                    float h = unsortedArray[mini];
                    unsortedArray[mini] = unsortedArray[i];
                    unsortedArray[i] = h;
                }
            }
        }

    }

    void Scape1() {
        float lim=(float)((screen.scope.home.water-0.5)/screen.scope.home.gravity*this.globalHeight);
        for (int j = 0; j < 16; j++) {
            ls1 = new LandScapeSection(screen.scope.home,10, globalDepth, globalFlatness, globalHeight, rnd1, rnd2, 63.0 - (double) (63.0 / (1 + j)));
            landHeight[j] = (float) ls1.h;
            for (int i = 0; i < lod; i++) {
                scape[j][lod - 1 - i] = new TessaShape();
                scape[j][lod - 1 - i].list = ls1.GetScape(lod - i, quad, ScapeSize, lim, rnd1, color);
                //TessaShape.VectorEquality(scape[j][lod - 1 - i].list);
            }
            genstage++;
        }
        finished1 = true;
    }

    void Scape2() {

        float lim=(float)((screen.scope.home.water-0.5)/screen.scope.home.gravity*this.globalHeight);
        for (int j = 16; j < 32; j++) {
            ls2 = new LandScapeSection(screen.scope.home,10, globalDepth, globalFlatness, globalHeight, rnd3, rnd4, 63.0 - (double) (63.0 / (1 + j)));

            landHeight[j] = (float) ls2.h;
            for (int i = 0; i < lod; i++) {
                scape[j][lod - 1 - i] = new TessaShape();
                scape[j][lod - 1 - i].list = ls2.GetScape(lod - i, quad, ScapeSize, lim, rnd3, color);
                //TessaShape.VectorEquality(scape[j][lod - 1 - i].list);
            }
            genstage++;
        }
        finished2 = true;
    }

    void Scape3() {

        float lim=(float)((screen.scope.home.water-0.5)/screen.scope.home.gravity*this.globalHeight);
        for (int j = 32; j < 48; j++) {
            ls3 = new LandScapeSection(screen.scope.home,10, globalDepth, globalFlatness, globalHeight,rnd1, rnd2, 63.0 - (double) (63.0 / (1 + j)));
            landHeight[j] = (float) ls3.h;
            for (int i = 0; i < lod; i++) {
                scape[j][lod - 1 - i] = new TessaShape();
                scape[j][lod - 1 - i].list = ls3.GetScape(lod - i, quad, ScapeSize, lim, rnd2, color);
                //TessaShape.VectorEquality(scape[j][lod - 1 - i].list);
            }
            genstage++;
        }
        finished3 = true;
    }

    void Scape4() {

        float lim=(float)((screen.scope.home.water-0.5)/screen.scope.home.gravity*this.globalHeight);
        for (int j = 48; j < 64; j++) {
            ls4 = new LandScapeSection(screen.scope.home,10, globalDepth, globalFlatness, globalHeight, rnd3, rnd4, 63.0 - (double) (63.0 / (1 + j)));
            landHeight[j] = (float) ls4.h;
            for (int i = 0; i < lod; i++) {
                scape[j][lod - 1 - i] = new TessaShape();
                scape[j][lod - 1 - i].list = ls4.GetScape(lod - i, quad, ScapeSize, lim, rnd4, color);
                //TessaShape.VectorEquality(scape[j][lod - 1 - i].list);
            }
            genstage++;
        }

        finished4 = true;
    }

// </editor-fold>
    public void Render() {

        //if (buffer.Ready()) {
        //buffer.Begin();
            /*Point at=new Point(0,0,0,quadR);
         int d=(int)(screen.cam.Sub(screen.cam, at).Length()/quadR);
         d=lod-d-1;
         if(d<0){d=0;}
         screen.LoadVertexTriangle(at, scape[0][d], 1.0,quadR,screen.renderList, DrawType.Triangle, DrawType.None, null);
         */
        if (this.screen.sky.night > 0) {
            this.suns = new RingList();
            this.suns.Append(screen.sky.sunLight);
        } else {
            if (this.screen.sky.asscention > 0) {
                this.suns = new RingList();
                //this.suns.Append(screen.sky.moonLight);

            } else {
                this.suns = null;
            }
        }
        double emissive1 = 0;//
        double emissive2 = 0;//
        if (screen.sky.night > 0) {
            emissive2 = (ScapeMiddle + screen.sky.night * ScapeSize);
        } else {
            emissive1 = (9 + screen.sky.night * 4 + screen.sky.asscention * 4)*0.125;
        }
        //System.out.println("Emissive1 "+emissive1);
        int ix = 0;
        for (int i = x - lod - range; i < x + lod + range; i++) {
            int x1 = Math.abs(ScapeMiddle + i) % ScapeSize;

            int iy = 0;
            for (int j = y - lod - range; j < y + lod + range; j++) {
                int y1 = (ScapeMiddle + j);

                Local checked = CheckXY(x1, y1);
                x1 = checked.x;
                y1 = checked.y;
                Point at = new Point(quadR * i, -(Math.abs((ix - lod + range)) * Math.abs((iy - lod + range))), quadR * j, quadR);
                int d = (int) ((screen.cam.Sub(screen.cam, at).Length() * 0.75 / quadR));
                d = lod - d;
                if (d < 0) {
                    d = 0;
                } else if (d >= lod) {
                    d = lod - 1;
                }
                
                screen.LoadVertexTriangle(at, scape[land[x1][y1]][d].list, 1.0, quadR, screen.renderList, DrawType.Triangle, DrawType.None, suns, emissive1);
                iy++;
            }
            ix++;
        }
        ix = 0;
        int lod2 = lod << 2;
        double lod3=(double) (lod2 * lod);
        for (int i = x - lod2; i < x + lod2; i++) {
            int x1 = Math.abs(ScapeMiddle + i) % ScapeSize;
            double xc=(ix - lod2)-screen.cam.fraction.x;
            int iy = 0;
            for (int j = y - lod2; j < y + lod2; j++) {
                int y1 = (ScapeMiddle + j);
                double yc=(iy - lod2)-screen.cam.fraction.y;
                Local checked = CheckXY(x1, y1);
                x1 = checked.x;
                y1 = checked.y;
                double curve =(-360.0)*(Math.sqrt(xc *xc + yc *yc ) / (lod3 / (Math.abs(xc) + Math.abs(yc))));
                Node<Base> b = this.elements[x1][y1].Start();
                for (; b.data != null; b = b.next) {
                    //b.data.mass.center
                    b.data.Render(this, curve, suns, emissive1);
                }
                iy++;
            }
            ix++;
        }
        Node<Craft> c = this.nearFighters.Start();

        for (; c.data != null; c = c.next) {
            screen.cam.rotation = c.data.rotation;
            screen.LoadVertexTriangle(c.data, c.data.shape.list, 1.0, c.data.mass.center.r, screen.renderList, DrawType.Triangle, DrawType.None, suns, emissive2);
            c.data.RenderParticles(screen);
        }

        screen.cam.rotation = null;
        Node<Craft> m = this.missiles.Start();

        for (; m.data != null; m = m.next) {
            //screen.cam.rotation=m.data.rotation;
            //screen.LoadVertexTriangle(m.data, m.data.shape.list, 1.0,m.data.mass.center.r , screen.renderList, DrawType.Triangle, DrawType.None, suns, emissive2);
            Point target = new Point();

            boolean ok = screen.LoadVertexPoints(m.data, m.data.Colours(), null, 1.0, m.data.mass.center.r, screen.renderList, DrawType.Circle, DrawType.None, target, 0);
            if (ok) {

                //System.out.println("Drawing Missile"+ok+" v"+m.data.velocity.v.x+" "+m.data.velocity.v.y+" "+m.data.velocity.v.z);
            }
        }
        Node<ParticlePoint> e = this.explosions.Start();

        for (; e.data != null; e = e.next) {
            e.data.Render(screen);
        }
        Node<Point> at = cloudPosBuffer.Start();
        Node<RingList<Tessa>> tri = cloudTriangleBuffer.Start();
        for (; at.data != null && tri.data != null; at = at.next, tri = tri.next) {

            screen.LoadVertexTriangle(at.data, tri.data, 1.0, 10, screen.renderList, DrawType.Triangle, DrawType.None, suns, emissive2);
        }

    }

    static Local CheckXY(int x1, int y1) {
        
            x1=Math.abs(x1);
            x1 %= ScapeSize;
        while (y1 < 0) {
            y1 = Math.abs(y1);
            x1 += ScapeMiddle;
            x1 %= ScapeSize;
        }
        while (y1 >= ScapeSize) {
            y1 = Math.abs(ScapeSize - (y1 - ScapeSize +1));
            x1 += ScapeMiddle;
            x1 %= ScapeSize;
            
           // System.out.println(y1);
        }
        return new Local(x1, y1);
    }

    void SortNearCraft() {
        this.nearFighters = new RingList();
        Node<Craft> c = this.fighters.Start();
        int x1 = x + ScapeMiddle - lod;
        int x2 = x + ScapeMiddle + lod;
        int y1 = y + ScapeMiddle - lod;
        int y2 = y + ScapeMiddle + lod;
        for (; c.data != null; c = c.next) {
            if(c.data.Dispose()){
                this.crashingCraft.Append(c.data);
            }else{
            //System.out.println(x1+" "+x2+" "+y1+" "+y2);
            //System.out.println(c.data.local.x+" "+c.data.local.y);
            if (c.data.local.x > x1 && c.data.local.x < x2 && c.data.local.y > y1 && c.data.local.y < y2) {
                this.nearFighters.Append(c.data);
            }
            }
        }
    }

    void Update(GameTime time) {
        double et = time.animTime / Physics.day;
        x = ((int) (screen.cam.x / quadR));
        y = ((int) (screen.cam.z / quadR));
        windAngle += (rnd1.NextDouble() - rnd2.NextDouble()) * et;
        airPresure += (rnd1.NextDouble() - rnd2.NextDouble()) * et;
        double airLim=screen.scope.home.atmospheres/screen.scope.home.gravity;
        double windLim=(screen.scope.home.revolvesRound.relative.mass/screen.scope.home.orbital.radius);
        double presureLim=airLim*windLim/PlanetMap.PISqrd;
        if (airPresure > airLim) {
            airPresure *= 0.5;
        }
        if (airPresure < presureLim) {
            airPresure += presureLim*rnd3.NextDouble();
        }
        windSpeed += (rnd3.NextDouble() - rnd4.NextDouble())*screen.scope.home.gravity* time.animTime / (Physics.hour * airPresure);
        windSpeed *= 1.0 - windLim*screen.scope.home.gravity* time.animTime/ Physics.hour;
        double temp=(1+Math.abs(Math.cos((y-ScapeMiddle)/ScapeMiddle*Math.PI)))*(273.25*screen.scope.home.relative.temperature-273.25)/12.0;
        cloudOffset.x += (Math.sin(windAngle) * windSpeed+Math.sin(screen.sky.sunAnglex)* temp)* time.animTime;
        cloudOffset.y += (Math.cos(windAngle) * windSpeed+Math.sin(screen.sky.sunAngley*0.5)* temp)* time.animTime;
        //buffer.End();
        double lim = quadR * lod;
        double lim2 = lim+lim;
        if (cloudOffset.x > lim) {
            cloudOffset.x -= lim2;
        } else if (cloudOffset.x < -lim) {

            cloudOffset.x += lim2;
        }
        if (cloudOffset.y > lim) {

            cloudOffset.y -= lim2;
        } else if (cloudOffset.y < -lim) {

            cloudOffset.y += lim2;
        }
        double ch = quadR * airPresure;
        double ch2 = ch * airPresure * 2;
        for (int i = x - lod + range; i < x + lod - range; i++) {
            int x1 = Math.abs(ScapeMiddle + i) % ScapeSize;
            for (int j = y - lod + range; j < y + lod - range; j++) {

                int y1 = (ScapeMiddle + j);

                Local checked = CheckXY(x1, y1);
                x1 = checked.x;
                y1 = checked.y;

                Cloud cloud = this.clouds[x1][y1];
                cloud.Update(time, quadR, airPresure);
                //
            }
        }

        Node<Craft> c = this.nearFighters.Start();

        for (; c.data != null; c = c.next) {
            if(c.data!=this.fighter){
                c.data.Update(time, screen.cam);
            }
        }
        Node<Base> b = this.bases.Start();
        for (; b.data != null; b = b.next) {
            b.data.Update(time, screen.cam);
            if(b.data.Dispose()){
            removeBases.Append(b.data);
            }
        }
        //}
        Game.exec.execute(this::UpdateCloudImages);
        SortNearCraft();
        UpdateMissiles(time);
        RemoveMissiles();
        UpdateExplosions(time);
        RemoveExplosions();
        RemoveBases();
        CrashCraft(time);
        RemoveCraft();
        //ai here
    }

    void UpdateMissiles(GameTime time) {

        Node<Craft> m = this.missiles.Start();

        for (; m.data != null; m = m.next) {
            m.data.Update(time, this.screen.cam);
            //System.out.println("Updating Missile");
        }
    }

    void RemoveMissiles() {
        if (this.removeMissiles.header.next != null) {
            Node<Craft> n = this.removeMissiles.header.next.Remove();
            if (n.data != null) {
                this.missiles.Remove(n.data);
            }
        }
    }
    void CrashCraft(GameTime time) {
        if (this.crashingCraft.header.next != null) {
            Node<Craft> n = this.crashingCraft.header.next.Remove();
            if (n.data != null && !n.data.crashing) {
                this.fighters.Remove(n.data); // .Remove(n.data);
                n.data.crashing=true;
            }else
            if(n.data != null && n.data.crashing)
            {
                if(n.data.y>0){
                n.data.Update(time,this.screen.cam);
                }else{
                //crash vehicle//
                if(n.data==this.fighter){
                  Game.currentScreen=1;//telecope screen/launch screen
                }else{
                  this.removeCraft.Append(n.data);
                  //add explosion here
                }
                }
            }
        }

    }
    void RemoveCraft() {
        if (this.removeCraft.header.next != null) {
            Node<Craft> n = this.removeCraft.header.next.Remove();
            if (n.data != null) {
                this.crashingCraft.Remove(n.data);
            }
        }

    }
    void RemoveBases() {
        if (this.removeBases.header.next != null) {
            Node<Base> n = this.removeBases.header.next.Remove();
            if (n.data != null) {
                if(n.data.r<1.0){
                
                this.bases.Remove(n.data);
                this.elements[n.data.local.x][n.data.local.y].Remove(n.data);
                }
            }
        }

    }

    void UpdateExplosions(GameTime time) {

        Node<ParticlePoint> e = this.explosions.Start();

        for (; e.data != null; e = e.next) {
            e.data.Update(time, this.screen.cam);
            if (e.data.Dispose()) {
                removeExplosions.Append(e.data);
            }
            //System.out.println("Updating Missile");
        }
    }

    void RemoveExplosions() {
        if (this.removeExplosions.header.next != null) {
            Node<ParticlePoint> n = this.removeExplosions.header.next.Remove();
            if (n.data != null) {
                this.explosions.Remove(n.data);
            }
        }

    }

    void UpdateCloudImages() {
        cloudPosBuffer = cloudPos;
        cloudTriangleBuffer = cloudTriangles;
        cloudPos = new RingList();
        cloudTriangles = new RingList();
        //Ground Zero Cloud
        double ch = quadR * airPresure;
        double ch2 = ch * airPresure * 2;
            /*double cix=(int)(cloudOffset.x)%(int)(lim);
            double ciy=(int)(cloudOffset.y)%(int)(lim);
            double cfx=(cloudOffset.x-(int)(cloudOffset.x))+cix;
            double cfy=(cloudOffset.y-(int)(cloudOffset.y))+ciy;*/
        for (int i = x - lod + range; i < x + lod - range; i++) {
            int x1 = Math.abs(ScapeMiddle + i) % ScapeSize;
            
                double ax=(quadR * i*3+cloudOffset.x)*0.5;
                
            for (int j = y - lod + range; j < y + lod - range; j++) {
                int y1 = (ScapeMiddle + j);

                Local checked = CheckXY(x1, y1);
                x1 = checked.x;
                y1 = checked.y;
                Cloud cloud = this.clouds[x1][y1];
                //cloud.Update(time,quadR,airPresure);
                RingList<Tessa> t = new RingList();
                double ar = 0;
                double k = 0;

                for (Node<Lerp> lin = cloud.lerps.Start(); lin.data != null && lin.next.data != null; lin = lin.next.next) {
                    double r1 = Math.sqrt(lin.data.at * lin.data.at + lin.next.data.at * lin.next.data.at) * ch;
                    double r2 = r1 * 2;
                    if (r2 > ar) {
                        ar = r2;
                    }
                    double cx = ch * lin.data.at * Math.sin(lin.data.at + k);
                    double cy = ch * lin.next.data.at * Math.cos(lin.data.at + k);
                    Vector v1 = new Vector(-cx, ch, cy);
                    Vector v2 = new Vector(cx, ch, cy);
                    Vector v3 = new Vector(cx, ch, -cy);
                    Vector v4 = new Vector(-cx, ch, cy);
                    Vector v5 = new Vector(0, ch + ch2, 0);
                    Normal n = new Normal(0, 1, 0);
                    Vector c = new Vector(1, 1, 1, 1);
                    TessaVertex tv1 = new TessaVertex(v1, n, c, r1);
                    TessaVertex tv2 = new TessaVertex(v2, n, c, r1);
                    TessaVertex tv3 = new TessaVertex(v3, n, c, r1);
                    TessaVertex tv4 = new TessaVertex(v4, n, c, r1);
                    TessaVertex tv5 = new TessaVertex(v5, n, c, r1);
                    Tessa t1 = new Tessa(tv1, tv2, tv3, r2);
                    Tessa t2 = new Tessa(tv3, tv4, tv1, r2);
                    //Tessa t3 = new Tessa(tv1, tv5, tv3, r2);
                    //Tessa t4 = new Tessa(tv4, tv5, tv2, r2);
                    //
                    /*Tessa t3 = new Tessa(tv1, tv5, tv2, r2);
                     Tessa t4 = new Tessa(tv2, tv5, tv3, r2);
                     Tessa t5 = new Tessa(tv3, tv5, tv1, r2);
                     Tessa t6 = new Tessa(tv3, tv5, tv4, r2);
                     Tessa t7 = new Tessa(tv4, tv5, tv1, r2);*/
                    t.add(t1);
                    t.add(t2);
                    //t.add(t3);
                    //t.add(t4);
                    //t.add(t3);
                    //t.add(t4);
                    //t.add(t5);
                    //t.add(t6);
                    //t.add(t7);
                    k += Math.PI;
                }
                cloudTriangles.Append(t);
                double ay=(quadR * j*3+cloudOffset.y)*0.5;
                cloudPos.Append(new Point(ax, 0,ay, ar));
            }
        }
    }
}

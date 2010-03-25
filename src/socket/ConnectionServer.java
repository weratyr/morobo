package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import filterData.FilterData;

public class ConnectionServer implements Runnable {
	private Socket client;
	private String message;
	private int sleepThread;

	public ConnectionServer(int sleepThread) {
		this.sleepThread = sleepThread;
	}

	public void readMessage(Socket socket)  {
		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		char[] buffer = new char[4];
		int numberOfChar = bufferedReader.read(buffer, 0, 4); // blockiert
		int toReceiveLength;
		toReceiveLength = (buffer[0] << 24);
		toReceiveLength += (buffer[1] << 16);
		toReceiveLength += (buffer[2] << 8);
		toReceiveLength += (buffer[3]);
	//	System.out.println(toReceiveLength);
		message = null;
		// bis Nachricht empfangen

		String receive = "";
		buffer = new char[toReceiveLength];
//		if (toReceiveLength > 20000) {
//			System.out.println("");;
//		} else {
//			buffer = new char[toReceiveLength];
//			while (receive.length() < toReceiveLength) {
//				numberOfChar =  bufferedReader.read(buffer, 0, toReceiveLength - receive.length());
//				receive += new String(buffer, 0, numberOfChar);
//				System.out.println("read blocks " + receive.length());
//			}
//	//		System.out.println("receive "+message);
//		}
		message = bufferedReader.readLine();
		System.out.println("message "+message);
		socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void writeMessage(Socket socket, String message) throws IOException {
		PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		printWriter.print(message);
		printWriter.flush();
	}

	public void waitForConnection(ServerSocket serverSocket) throws IOException {
		Socket socket = serverSocket.accept();
		client = socket;
	}

	public String getMessage() {
		return message;
	}

	public void run() {
		try {
			int port = 5678;
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("server is online");
			int i = 1;
			while (true) {
				waitForConnection(serverSocket);
				readMessage(client);
//				try {
//					Thread.sleep(sleepThread);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
				message =  null; //"<map><name>Kette</name><pos>"+(40+i*2)+","+(400+i*2)+"</pos><direction>0,0</direction><data>-22,40</data><data>-22,40</data><data>-22,40</data><data>-21,40</data><data>-21,40</data><data>-21,40</data><data>-20,40</data><data>-20,40</data><data>-20,40</data><data>-20,40</data><data>-19,41</data><data>-19,41</data><data>-19,41</data><data>-19,41</data><data>-18,40</data><data>-18,40</data><data>-18,40</data><data>-17,40</data><data>-17,41</data><data>-17,41</data><data>-17,41</data><data>-16,41</data><data>-16,41</data><data>-16,41</data><data>-16,41</data><data>-15,41</data><data>-15,41</data><data>-15,41</data><data>-15,42</data><data>-14,42</data><data>-14,42</data><data>-14,42</data><data>-14,42</data><data>-13,41</data><data>-13,41</data><data>-12,41</data><data>-12,41</data><data>-12,41</data><data>-12,41</data><data>-11,41</data><data>-11,42</data><data>-11,42</data><data>-11,42</data><data>-10,42</data><data>-10,42</data><data>-10,42</data><data>-10,42</data><data>-9,42</data><data>-9,42</data><data>-9,42</data><data>-9,42</data><data>-8,42</data><data>-8,42</data><data>-8,41</data><data>-7,41</data><data>-7,41</data><data>-7,42</data><data>-7,41</data><data>-6,42</data><data>-6,42</data><data>-6,42</data><data>-6,42</data><data>-5,42</data><data>-5,42</data><data>-5,42</data><data>-5,42</data><data>-4,42</data><data>-4,42</data><data>-4,42</data><data>-4,42</data><data>-3,42</data><data>-3,41</data><data>-3,41</data><data>-3,41</data><data>-2,41</data><data>-2,41</data><data>-2,41</data><data>-2,41</data><data>-1,41</data><data>-1,41</data><data>-1,41</data><data>-1,41</data><data>0,41</data><data>0,41</data><data>0,41</data><data>1,41</data><data>1,41</data><data>1,41</data><data>1,41</data><data>2,41</data><data>2,41</data><data>2,41</data><data>2,41</data><data>3,41</data><data>3,41</data><data>3,41</data><data>3,41</data><data>4,41</data><data>4,41</data><data>4,41</data><data>4,41</data><data>5,41</data><data>5,42</data><data>5,42</data><data>12,91</data><data>13,92</data><data>14,95</data><data>14,95</data><data>46,297</data><data>48,297</data><data>50,297</data><data>51,297</data><data>53,296</data><data>55,296</data><data>57,296</data><data>59,295</data><data>61,295</data><data>62,294</data><data>64,294</data><data>66,294</data><data>68,293</data><data>70,293</data><data>71,292</data><data>73,292</data><data>75,292</data><data>77,291</data><data>78,291</data><data>80,290</data><data>82,290</data><data>84,289</data><data>86,289</data><data>87,288</data><data>89,287</data><data>91,287</data><data>93,286</data><data>94,286</data><data>96,285</data><data>98,285</data><data>100,284</data><data>101,283</data><data>103,283</data><data>105,282</data><data>107,281</data><data>108,281</data><data>110,280</data><data>112,279</data><data>113,279</data><data>115,278</data><data>117,277</data><data>119,277</data><data>120,276</data><data>122,275</data><data>124,274</data><data>125,274</data><data>127,273</data><data>129,272</data><data>130,271</data><data>132,271</data><data>134,270</data><data>135,269</data><data>137,268</data><data>139,267</data><data>140,266</data><data>142,265</data><data>144,265</data><data>145,264</data><data>147,263</data><data>148,262</data><data>150,261</data><data>152,260</data><data>153,259</data><data>155,258</data><data>156,257</data><data>158,256</data><data>159,255</data><data>161,254</data><data>163,253</data><data>164,252</data><data>166,251</data><data>167,250</data><data>169,249</data><data>170,248</data><data>172,247</data><data>173,246</data><data>175,245</data><data>176,244</data><data>178,243</data><data>179,242</data><data>181,241</data><data>182,240</data><data>184,238</data><data>185,237</data><data>187,236</data><data>188,235</data><data>190,234</data><data>191,233</data><data>192,232</data><data>194,230</data><data>195,229</data><data>197,228</data><data>198,227</data><data>199,225</data><data>201,224</data><data>202,223</data><data>204,222</data><data>205,221</data><data>206,219</data><data>208,218</data><data>209,217</data><data>210,215</data><data>212,214</data><data>213,213</data><data>214,212</data><data>215,210</data><data>217,209</data><data>218,208</data><data>219,206</data><data>221,205</data><data>222,204</data><data>223,202</data><data>224,201</data><data>225,199</data><data>227,198</data><data>228,197</data><data>229,195</data><data>230,194</data><data>232,192</data><data>233,191</data><data>234,190</data><data>235,188</data><data>236,187</data><data>237,185</data><data>238,184</data><data>240,182</data><data>241,181</data><data>242,179</data><data>243,178</data><data>244,176</data><data>245,175</data><data>246,173</data><data>247,172</data><data>248,170</data><data>249,169</data><data>250,167</data><data>251,166</data><data>204,133</data><data>202,130</data><data>192,121</data><data>191,119</data><data>189,116</data><data>186,113</data><data>187,112</data><data>188,111</data><data>188,110</data><data>189,109</data><data>190,107</data><data>190,106</data><data>191,105</data><data>193,104</data><data>195,104</data><data>196,103</data><data>197,102</data><data>198,101</data><data>198,100</data><data>199,99</data><data>200,98</data><data>202,97</data><data>221,105</data><data>229,107</data><data>230,105</data><data>231,104</data><data>231,103</data><data>231,101</data><data>231,99</data><data>231,97</data><data>231,96</data><data>232,94</data><data>232,93</data><data>232,91</data><data>230,89</data><data>227,86</data><data>227,84</data><data>227,83</data><data>228,82</data><data>228,80</data><data>228,78</data><data>228,77</data><data>229,76</data><data>229,74</data><data>230,73</data><data>230,71</data><data>230,70</data><data>230,68</data><data>231,67</data><data>231,65</data><data>234,65</data><data>235,63</data><data>235,62</data><data>235,60</data><data>235,59</data><data>235,57</data><data>234,56</data><data>234,54</data><data>233,52</data><data>234,51</data><data>234,49</data><data>234,48</data><data>233,46</data><data>234,45</data><data>234,44</data><data>234,42</data><data>234,41</data><data>235,39</data><data>231,37</data><data>231,36</data><data>231,34</data><data>233,33</data><data>233,32</data><data>233,30</data><data>233,29</data><data>233,27</data><data>234,26</data><data>233,24</data><data>233,23</data><data>233,22</data><data>233,20</data><data>232,19</data><data>203,15</data><data>203,14</data><data>203,12</data><data>202,11</data><data>202,10</data><data>202,9</data><data>202,7</data><data>201,6</data><data>200,5</data><data>200,4</data><data>200,2</data><data>198,1</data><data>198,0</data><data>198,-1</data><data>188,-2</data><data>188,-3</data><data>99,-2</data><data>98,-3</data><data>98,-4</data><data>98,-4</data><data>199,-10</data><data>199,-11</data><data>199,-12</data><data>199,-13</data><data>198,-15</data><data>199,-16</data><data>199,-17</data><data>201,-19</data><data>201,-20</data><data>201,-21</data><data>201,-22</data><data>201,-23</data><data>200,-25</data><data>201,-26</data><data>0,0</data><data>0,0</data><data>0,0</data><data>231,-36</data><data>231,-37</data><data>230,-38</data><data>231,-40</data><data>230,-41</data><data>296,-55</data><data>296,-57</data><data>295,-59</data><data>295,-61</data><data>294,-62</data><data>294,-64</data><data>294,-66</data><data>293,-68</data><data>293,-70</data><data>292,-71</data><data>92,-23</data><data>90,-23</data><data>90,-24</data><data>291,-78</data><data>87,-24</data><data>87,-25</data><data>88,-26</data><data>88,-26</data><data>88,-27</data><data>89,-28</data><data>89,-28</data><data>88,-29</data><data>89,-29</data><data>89,-30</data><data>89,-31</data><data>88,-31</data><data>89,-32</data><data>87,-32</data><data>87,-32</data><data>89,-34</data><data>91,-35</data><data>89,-35</data><data>90,-36</data><data>90,-37</data><data>91,-38</data><data>90,-38</data><data>91,-39</data><data>91,-40</data><data>91,-40</data><data>90,-41</data><data>91,-42</data><data>91,-42</data><data>272,-129</data><data>271,-130</data><data>271,-132</data><data>270,-134</data><data>269,-135</data><data>268,-137</data><data>267,-139</data><data>266,-140</data><data>265,-142</data><data>265,-144</data><data>264,-145</data><data>263,-147</data><data>262,-148</data><data>261,-150</data><data>260,-152</data><data>0,0</data><data>0,0</data><data>0,0</data><data>140,-87</data><data>139,-87</data><data>137,-87</data><data>135,-86</data><data>133,-87</data><data>133,-88</data><data>132,-88</data><data>132,-89</data><data>131,-90</data><data>131,-91</data><data>132,-93</data><data>131,-94</data><data>130,-94</data><data>136,-99</data><data>135,-100</data><data>134,-101</data><data>134,-102</data><data>131,-101</data><data>130,-102</data><data>129,-102</data><data>129,-103</data><data>128,-104</data><data>126,-103</data><data>123,-102</data><data>122,-103</data><data>120,-102</data><data>116,-100</data><data>105,-91</data><data>104,-92</data><data>103,-92</data><data>102,-93</data><data>102,-93</data><data>102,-95</data><data>102,-96</data><data>101,-97</data><data>101,-97</data><data>100,-98</data><data>96,-95</data><data>47,-47</data><data>46,-47</data><data>46,-47</data><data>209,-217</data><data>208,-218</data><data>206,-219</data><data>205,-221</data><data>204,-222</data><data>202,-223</data><data>201,-224</data><data>199,-225</data><data>198,-227</data><data>197,-228</data><data>195,-229</data><data>194,-230</data><data>192,-232</data><data>191,-233</data><data>190,-234</data><data>188,-235</data><data>187,-236</data><data>185,-237</data><data>184,-238</data><data>182,-240</data><data>181,-241</data><data>179,-242</data><data>178,-243</data><data>176,-244</data><data>175,-245</data><data>173,-246</data><data>172,-247</data><data>170,-248</data><data>169,-249</data><data>167,-250</data><data>166,-251</data><data>164,-252</data><data>163,-253</data><data>161,-254</data><data>159,-255</data><data>158,-256</data><data>156,-257</data><data>155,-258</data><data>153,-259</data><data>152,-260</data><data>150,-261</data><data>148,-262</data><data>147,-263</data><data>145,-264</data><data>144,-265</data><data>142,-265</data><data>140,-266</data><data>139,-267</data><data>137,-268</data><data>135,-269</data><data>134,-270</data><data>132,-271</data><data>130,-271</data><data>129,-272</data><data>127,-273</data><data>125,-274</data><data>124,-274</data><data>122,-275</data><data>120,-276</data><data>119,-277</data><data>117,-277</data><data>115,-278</data><data>113,-279</data><data>112,-279</data><data>110,-280</data><data>108,-281</data><data>107,-281</data><data>105,-282</data><data>103,-283</data><data>101,-283</data><data>100,-284</data><data>98,-285</data><data>96,-285</data><data>94,-286</data><data>93,-286</data><data>91,-287</data><data>89,-287</data><data>87,-288</data><data>86,-289</data><data>84,-289</data><data>10,-35</data><data>10,-35</data><data>9,-35</data><data>9,-35</data><data>9,-34</data><data>9,-34</data><data>8,-34</data><data>8,-34</data><data>8,-34</data><data>8,-34</data><data>7,-34</data><data>7,-35</data><data>7,-35</data><data>7,-36</data><data>7,-36</data><data>7,-37</data><data>13,-70</data><data>13,-73</data><data>12,-73</data><data>12,-73</data><data>11,-73</data><data>11,-73</data><data>10,-73</data><data>10,-73</data><data>10,-73</data><data>9,-73</data><data>9,-73</data><data>8,-74</data><data>8,-74</data><data>7,-74</data><data>7,-73</data><data>6,-73</data><data>6,-73</data><data>5,-73</data><data>5,-73</data><data>4,-73</data><data>4,-73</data><data>4,-73</data><data>3,-73</data><data>3,-72</data><data>2,-73</data><data>2,-72</data><data>1,-72</data><data>1,-72</data><data>0,-73</data><data>0,-72</data><data>0,-73</data><data>-1,-73</data><data>-1,-73</data><data>-2,-72</data><data>-2,-72</data><data>-3,-72</data><data>-3,-72</data><data>-4,-72</data><data>-4,-73</data><data>-4,-73</data><data>-5,-73</data><data>-5,-73</data><data>-6,-72</data><data>-6,-72</data><data>-7,-72</data><data>-7,-72</data><data>-7,-72</data><data>-8,-72</data><data>-8,-71</data><data>-9,-70</data><data>-9,-70</data><data>-10,-70</data><data>-10,-69</data><data>-10,-69</data><data>-11,-69</data><data>-11,-69</data><data>-12,-69</data><data>-12,-70</data><data>-13,-71</data><data>-13,-72</data><data>-14,-73</data><data>-15,-75</data><data>-15,-74</data><data>-16,-74</data><data>-16,-74</data><data>-17,-75</data><data>-17,-75</data><data>-18,-75</data><data>-18,-75</data><data>-19,-75</data><data>-19,-75</data><data>-20,-74</data><data>-19,-71</data><data>-19,-69</data><data>-19,-68</data><data>-19,-67</data><data>-20,-66</data><data>-20,-66</data><data>-20,-66</data><data>-21,-66</data><data>-22,-67</data><data>-22,-66</data><data>-22,-66</data><data>-23,-67</data><data>-24,-68</data><data>-24,-68</data><data>-25,-68</data><data>-25,-67</data><data>-25,-67</data><data>-26,-67</data><data>-26,-67</data><data>-27,-67</data><data>-27,-67</data><data>-28,-67</data><data>-28,-66</data><data>-28,-66</data><data>-29,-66</data><data>-29,-66</data><data>-30,-66</data><data>-30,-65</data><data>-31,-66</data><data>-31,-65</data><data>-32,-66</data><data>-32,-66</data><data>-32,-65</data><data>-33,-65</data><data>-33,-65</data><data>-34,-65</data><data>-34,-65</data><data>-35,-65</data><data>-35,-65</data><data>-36,-66</data><data>-37,-65</data><data>-22,40</data><data>-22,40</data><data>-22,40</data><data>-22,41</data><data>-21,41</data><data>-21,40</data><data>-20,40</data><data>-20,40</data><data>-20,40</data><data>-19,40</data><data>-19,40</data><data>-19,40</data><data>-19,40</data><data>-18,40</data><data>-18,40</data><data>-18,40</data><data>-18,40</data><data>-17,40</data><data>-17,41</data><data>-17,41</data><data>-17,41</data><data>-16,41</data><data>-16,41</data><data>-15,40</data><data>-15,40</data><data>-15,40</data><data>-15,40</data><data>-14,40</data><data>-14,41</data><data>-14,41</data><data>-14,41</data><data>-13,41</data><data>-13,41</data><data>-13,41</data><data>-13,41</data><data>-12,41</data><data>-12,41</data><data>-12,41</data><data>-12,41</data><data>-11,41</data><data>-11,42</data><data>-11,42</data><data>-11,42</data><data>-10,42</data><data>-10,42</data><data>-10,42</data></map>";
				i+=2;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// public static void main(String[] args) {

	// }
}

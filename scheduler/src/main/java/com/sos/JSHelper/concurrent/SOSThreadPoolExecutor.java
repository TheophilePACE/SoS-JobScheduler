/**
 * Copyright (C) 2014 BigLoupe http://bigloupe.github.io/SoS-JobScheduler/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
/********************************************************* begin of preamble
**
** Copyright (C) 2003-2012 Software- und Organisations-Service GmbH. 
** All rights reserved.
**
** This file may be used under the terms of either the 
**
**   GNU General Public License version 2.0 (GPL)
**
**   as published by the Free Software Foundation
**   http://www.gnu.org/licenses/gpl-2.0.txt and appearing in the file
**   LICENSE.GPL included in the packaging of this file. 
**
** or the
**  
**   Agreement for Purchase and Licensing
**
**   as offered by Software- und Organisations-Service GmbH
**   in the respective terms of supply that ship with this file.
**
** THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
** IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
** THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
** PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS
** BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
** CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
** SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
** INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
** CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
** ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
** POSSIBILITY OF SUCH DAMAGE.
********************************************************** end of preamble*/
package com.sos.JSHelper.concurrent;

/**
 * @author KB
 *
 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SOSThreadPoolExecutor {
	//Parallel running Threads(Executor) on System
	public int									corePoolSize	= 5;

	//Maximum Threads allowed in Pool
	public int									maxPoolSize		= 8;

	//Keep alive time for waiting threads for jobs(Runnable)
	public long								keepAliveTime	= 10;

	//This is the one who manages and start the work
	public ExecutorService						objThreadPool	= null;

	//Working queue for jobs (Runnable). We add them finally here
//	private final ArrayBlockingQueue<Runnable>	workQueue		= new ArrayBlockingQueue<Runnable>(maxPoolSize + 1);

	public SOSThreadPoolExecutor() {
		objThreadPool = Executors.newFixedThreadPool(corePoolSize);
	}

	public SOSThreadPoolExecutor(final int pintCorePoolSize) {
		objThreadPool = Executors.newFixedThreadPool(pintCorePoolSize);
		corePoolSize = pintCorePoolSize;
	}

	@SuppressWarnings("unchecked")
	public Future<Runnable> runTask(final Runnable task) {
//				objThreadPool.execute(task);
		Future<Runnable> objFut = (Future<Runnable>) objThreadPool.submit(task);
//				System.out.println("Tasks in workQueue.." + workQueue.size());
		return objFut;
	}

	public void shutDown() {
		objThreadPool.shutdown();
	}

	public static void main(final String args[]) {
		SOSThreadPoolExecutor mtpe = new SOSThreadPoolExecutor(10);
		int cpus = Runtime.getRuntime().availableProcessors();
		System.out.println("max avl cpus = " + cpus) ;
		for (int i = 0; i < 19; i++) {
			Future objF = mtpe.runTask(new WorkerRunnable(i));
		}

		mtpe.shutDown();
		System.out.println("Finished! ");
	}

	private static class WorkerRunnable implements Runnable {

		private final int	jobNr;

		/**
		 *
		 * @param jobNr number for displaying
		 */
		public WorkerRunnable(final int jobNr) {
			this.jobNr = jobNr;
		}

		@Override
		public void run() {
			for (int i = 0; i < 10; i++) {

				try {
					long intNo = Thread.currentThread().getId();
					System.out.println("Task " + jobNr + ", calculated " + i + ", Thread ID = " + intNo);
					Thread.currentThread().sleep(100);
				}
				catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
		}
	}
}

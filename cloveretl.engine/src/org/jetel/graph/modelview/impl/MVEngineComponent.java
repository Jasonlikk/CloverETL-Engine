/*
 * jETeL/CloverETL - Java based ETL application framework.
 * Copyright (c) Javlin, a.s. (info@cloveretl.com)
 *  
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.jetel.graph.modelview.impl;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jetel.component.ComponentMetadataProvider;
import org.jetel.component.MetadataProvider;
import org.jetel.graph.Node;
import org.jetel.graph.modelview.MVComponent;
import org.jetel.graph.modelview.MVEdge;
import org.jetel.graph.modelview.MVGraph;
import org.jetel.graph.modelview.MVMetadata;
import org.jetel.graph.modelview.MVSubgraph;
import org.jetel.metadata.DataRecordMetadata;
import org.jetel.metadata.MetadataRepository;
import org.jetel.util.SubgraphUtils;
import org.jetel.util.compile.ClassLoaderUtils;
import org.jetel.util.string.StringUtils;

/**
 * General model wrapper for engine component ({@link Node}).
 * 
 * @author Kokon (info@cloveretl.com)
 *         (c) Javlin, a.s. (www.cloveretl.com)
 *
 * @created 27. 8. 2013
 */
public class MVEngineComponent extends MVEngineGraphElement implements MVComponent {

	private static final long serialVersionUID = -7445666999175898101L;

	private Map<Integer, MVEdge> inputEdges;

	private Map<Integer, MVEdge> outputEdges;
	
	MVEngineComponent(Node engineComponent, MVGraph parentMVGraph) {
		super(engineComponent, parentMVGraph);
		
		inputEdges = new LinkedHashMap<Integer, MVEdge>();
		for (Entry<Integer, org.jetel.graph.InputPort> entry : engineComponent.getInputPorts().entrySet()) {
			inputEdges.put(entry.getKey(), parentMVGraph.getMVEdge(entry.getValue().getEdge().getId()));
		}
		
		outputEdges = new LinkedHashMap<Integer, MVEdge>();
		for (Entry<Integer, org.jetel.graph.OutputPort> entry : engineComponent.getOutputPorts().entrySet()) {
			outputEdges.put(entry.getKey(), parentMVGraph.getMVEdge(entry.getValue().getEdge().getId()));
		}
	}

	@Override
	public Node getModel() {
		return (Node) super.getModel();
	}
	
	@Override
	public void reset() {
		//DO NOTHING
	}
	
	@Override
	public Map<Integer, MVEdge> getInputEdges() {
		return inputEdges;
	}

	@Override
	public Map<Integer, MVEdge> getOutputEdges() {
		return outputEdges;
	}

	@Override
	public boolean isPassThrough() {
		return getModel().getDescriptor().isPassThrough();
	}

	/**
	 * @return metadata provider based on engine component
	 */
	private static MetadataProvider getMetadataProvider(Node engineComponent) {
		MetadataProvider metadataProvider = null;
		if (engineComponent instanceof MetadataProvider) {
			metadataProvider = (MetadataProvider) engineComponent;
		} else if (engineComponent != null && !StringUtils.isEmpty(engineComponent.getDescriptor().getMetadataProvider())) {
			metadataProvider = ClassLoaderUtils.loadClassInstance(MetadataProvider.class, engineComponent.getDescriptor().getMetadataProvider(), engineComponent);
			if (metadataProvider instanceof ComponentMetadataProvider) {
				((ComponentMetadataProvider) metadataProvider).setComponent(engineComponent);
			}
		}
		return metadataProvider;
	}
	
	@Override
	public MVMetadata getDefaultOutputMetadata(int portIndex, MetadataPropagationResolver metadataPropagationResolver) {
		//first let's try to find default metadata dynamically from component instance
		MetadataProvider metadataProvider = getMetadataProvider(getModel());
		if (metadataProvider != null) {
			MVMetadata metadata = metadataProvider.getOutputMetadata(portIndex, metadataPropagationResolver);
			if (metadata != null) {
				return metadata;
			}
		}

		//no dynamic metadata found, let's use static metadata from component descriptor 
		String metadataId = getModel().getDescriptor().getDefaultOutputMetadataId(portIndex);
		return metadataId != null ? getStaticMetadata(metadataId) : null;
	}

	@Override
	public MVMetadata getDefaultInputMetadata(int portIndex, MetadataPropagationResolver metadataPropagationResolver) {
		//first let's try to find default metadata dynamically from component instance
		MetadataProvider metadataProvider = getMetadataProvider(getModel());
		if (metadataProvider != null) {
			MVMetadata metadata = metadataProvider.getInputMetadata(portIndex, metadataPropagationResolver);
			if (metadata != null) {
				return metadata;
			}
		}
		//no dynamic metadata found, let's use statical metadata from component descriptor 
		String metadataId = getModel().getDescriptor().getDefaultInputMetadataId(portIndex);
		return metadataId != null ? getStaticMetadata(metadataId) : null;
	}
	
	/**
	 * @return metadata from static metadata repository
	 */
	private MVMetadata getStaticMetadata(String metadataId) {
		DataRecordMetadata metadata = MetadataRepository.getMetadata(metadataId, getModel());
		if (metadata != null) {
			return getParent().createMVMetadata(metadata);
		} else {
			return null;
		}
	}
	
	@Override
	public MVGraph getParent() {
		return (MVGraph) super.getParent();
	}

	@Override
	public boolean isSubgraphComponent() {
		return SubgraphUtils.isSubJobComponent(getModel().getType());
	}
	
	@Override
	public boolean isSubgraphInputComponent() {
		return SubgraphUtils.isSubJobInputComponent(getModel().getType());
	}
	
	@Override
	public boolean isSubgraphOutputComponent() {
		return SubgraphUtils.isSubJobOutputComponent(getModel().getType());
	}

	@Override
	public MVSubgraph getSubgraph() {
		if (isSubgraphComponent()) {
			return getParentMVGraph().getMVSubgraphs().get(this);
		} else {
			throw new IllegalStateException();
		}
	}
	
}

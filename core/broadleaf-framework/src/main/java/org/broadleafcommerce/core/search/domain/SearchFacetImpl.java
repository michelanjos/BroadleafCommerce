/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.core.search.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_SEARCH_FACET")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "blStandardElements")
public class SearchFacetImpl implements SearchFacet,java.io.Serializable {
	
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "SearchFacetId", strategy = GenerationType.TABLE)
    @TableGenerator(name = "SearchFacetId", table = "SEQUENCE_GENERATOR", pkColumnName = "ID_NAME", valueColumnName = "ID_VAL", pkColumnValue = "SearchFacetImpl", allocationSize = 50)
    @Column(name = "SEARCH_FACET_ID")
    @AdminPresentation(friendlyName = "SearchFacetImpl_ID", order = 1, group = "SearchFacetImpl_description", groupOrder = 1, visibility = VisibilityEnum.HIDDEN_ALL)
    protected Long id;
    
    @ManyToOne(optional=false, targetEntity = FieldImpl.class)
    @JoinColumn(name = "FIELD_ID")
    @AdminPresentation(friendlyName = "SearchFacetImpl_field",  order = 2,group = "SearchFacetImpl_description", excluded = true, visibility = VisibilityEnum.GRID_HIDDEN)
    protected Field field;
    
    @Column(name = "LABEL")
    @AdminPresentation(friendlyName = "SearchFacetImpl_label", order = 3, group = "SearchFacetImpl_description", groupOrder = 1)
    protected String label;
    
    @Column(name =  "SHOW_ON_SEARCH")
    @AdminPresentation(friendlyName = "SearchFacetImpl_showOnSearch", order = 4, group = "SearchFacetImpl_description", groupOrder = 1,prominent=false)
    protected Boolean showOnSearch = false;
    
    @Column(name = "SEARCH_DISPLAY_PRIORITY")
    @AdminPresentation(friendlyName = "SearchFacetImpl_searchPriority", order = 5, group = "SearchFacetImpl_description", groupOrder = 1, prominent=true)
    protected Integer searchDisplayPriority = 1;
    
    @Column(name = "MULTISELECT")
    @AdminPresentation(friendlyName = "SearchFacetImpl_multiselect", order = 6, group = "SearchFacetImpl_description", groupOrder = 1, prominent=true)
    protected Boolean canMultiselect = true;
    
    @OneToMany(mappedBy = "searchFacet", targetEntity = SearchFacetRangeImpl.class, cascade = {CascadeType.ALL})
    @Cascade(value={org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
    protected List<SearchFacetRange> searchFacetRanges  = new ArrayList<SearchFacetRange>();
    
    @OneToMany(mappedBy = "searchFacet", targetEntity = RequiredFacetImpl.class, cascade = {CascadeType.ALL})
    @Cascade(value={org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST})    
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
    @BatchSize(size = 50)
    protected List<RequiredFacet> requiredFacets = new ArrayList<RequiredFacet>();
    
    @Column(name = "REQUIRES_ALL_DEPENDENT")
    @AdminPresentation(friendlyName = "SearchFacetImpl_requiresAllDependentFacets", order = 6, group = "SearchFacetImpl_description", groupOrder = 1, prominent=true)
    protected Boolean requiresAllDependentFacets = false;
    
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public Field getField() {
		return field;
	}

	@Override
	public void setField(Field field) {
		this.field = field;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public Boolean getShowOnSearch() {
		return showOnSearch;
	}

	@Override
	public void setShowOnSearch(Boolean showOnSearch) {
		this.showOnSearch = showOnSearch;
	}

	@Override
	public Integer getSearchDisplayPriority() {
		return searchDisplayPriority;
	}

	@Override
	public void setSearchDisplayPriority(Integer searchDisplayPriority) {
		this.searchDisplayPriority = searchDisplayPriority;
	}
	
	@Override
	public Boolean getCanMultiselect() {
		return canMultiselect;
	}

	@Override
	public void setCanMultiselect(Boolean canMultiselect) {
		this.canMultiselect = canMultiselect;
	}
	
	@Override
    public List<RequiredFacet> getRequiredFacets() {
        return requiredFacets;
    }

	@Override
    public void setRequiredFacets(List<RequiredFacet> requiredFacets) {
        this.requiredFacets = requiredFacets;
    }

	@Override
    public Boolean getRequiresAllDependentFacets() {
        return requiresAllDependentFacets == null ? false : requiresAllDependentFacets;
    }

	@Override
    public void setRequiresAllDependentFacets(Boolean requiresAllDependentFacets) {
        this.requiresAllDependentFacets = requiresAllDependentFacets;
    }

    @Override
	public List<SearchFacetRange> getSearchFacetRanges() {
		return searchFacetRanges;
	}

	@Override
	public void setSearchFacetRanges(List<SearchFacetRange> searchFacetRanges) {
		this.searchFacetRanges = searchFacetRanges;
	}
	
	@Override
	public boolean equals(Object obj) {
	   	if (this == obj) {
            return true;
        }
	    if (obj == null) {
            return false;
        }
	    if (getClass() != obj.getClass()) {
            return false;
        }
        SearchFacet other = (SearchFacet) obj;
        
        return getField().equals(other.getField());
    }

}
